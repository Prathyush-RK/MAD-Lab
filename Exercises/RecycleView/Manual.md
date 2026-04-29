# Android Development | Kotlin
## Step-by-Step Manual — Simple RecyclerView Experiment

> Build a scrollable list with a custom Adapter, ViewHolder, and item click handling

---

## 📋 What You Will Build

A fully functional Android app that displays a scrollable list of fruit names using RecyclerView — the modern, efficient replacement for ListView.

You will implement: a data model, a custom Adapter, a ViewHolder pattern, a list item XML layout, item click handling with Toast messages, and dynamic item addition and removal at runtime.

---

## Section 0: RecyclerView Architecture

Before writing any code, understanding how RecyclerView's components interact will make every subsequent step much clearer.

| Component | Class / File | Responsibility |
|---|---|---|
| RecyclerView | `res/layout/activity_main.xml` | The scrollable container widget placed in the Activity layout. Manages scrolling, recycling, and positioning of item views. |
| LayoutManager | `MainActivity.kt` | Controls how items are arranged. `LinearLayoutManager` gives a vertical/horizontal list. `GridLayoutManager` gives a grid. |
| Adapter | `FruitAdapter.kt` | The bridge between your data list and the RecyclerView. Creates ViewHolder objects and binds data to them. |
| ViewHolder | `FruitAdapter.kt` (inner class) | Holds references to the views inside one list item. Avoids repeated `findViewById()` calls — the core performance optimisation. |
| Item Layout | `res/layout/item_fruit.xml` | The XML layout for a single row/card in the list. Inflated once per ViewHolder. |
| Data Model | `Fruit.kt` | A data class representing one list item (e.g. name, emoji). |

### 📋 The Recycling Mechanism

RecyclerView creates only enough ViewHolder objects to fill the visible screen (plus a small buffer).

When you scroll and an item moves off-screen, its ViewHolder is **NOT** destroyed — it is placed in a *recycled pool* and reused for the next incoming item. `onBindViewHolder()` re-fills it with new data. This is why it is orders of magnitude faster than ListView for large datasets.

---

## Section 1: Prerequisites

| Requirement | Details |
|---|---|
| Android Studio | Hedgehog (2023.1.1) or later — download at developer.android.com |
| Kotlin | Built-in with Android Studio; basic syntax knowledge required |
| Android SDK | API level 21 (Android 5.0 Lollipop) minimum — covers 99%+ of devices |
| Device / AVD | Physical Android device or Android Virtual Device via AVD Manager |
| RecyclerView Library | Part of AndroidX — added via build.gradle dependency |

### 📋 No External Library Needed

RecyclerView ships as part of AndroidX (`androidx.recyclerview:recyclerview`). You do **NOT** need to download anything separately — one line in `build.gradle` is all it takes.

---

## Section 2: Project Setup

### STEP 1 — Create a New Android Project

Launch Android Studio and configure a fresh project.

1. Go to **File → New → New Project**
2. Select **"Empty Views Activity"** and click **Next**
3. Fill in the project details:
   - **Name:** `RecyclerViewDemo`
   - **Package name:** `com.example.recyclerviewdemo`
   - **Language:** Kotlin
   - **Minimum SDK:** API 21 (Android 5.0)
4. Click **Finish** and wait for the Gradle sync to complete

### STEP 2 — Add the RecyclerView Dependency

Edit `app/build.gradle` to include the AndroidX RecyclerView library.

Navigate to **app → build.gradle (Module: app)**. In the `dependencies` block, add:

```groovy
// app/build.gradle
dependencies {
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    // RecyclerView — add this line
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
}
```

After editing, click **Sync Now** in the yellow banner at the top of the file. Wait for the sync to finish before proceeding.

### 📋 Kotlin DSL Users (build.gradle.kts)

If your project uses Kotlin DSL, the syntax is slightly different:

```kotlin
implementation("androidx.recyclerview:recyclerview:1.3.2")
```

Check the file extension: `.gradle` = Groovy DSL, `.gradle.kts` = Kotlin DSL.

---

## Section 3: Create the Data Model

### STEP 3 — Create `Fruit.kt` — The Data Class

A data class represents one item in the list.

1. In Android Studio's Project view, right-click on the package folder
2. Choose **New → Kotlin Class/File**
3. Select **Data Class** from the dropdown
4. Name it `Fruit` and press Enter

Replace the entire file content with:

```kotlin
package com.example.recyclerviewdemo

// A data class automatically generates:
// equals(), hashCode(), toString(), copy()
data class Fruit(
    val name: String,   // Display name, e.g. "Apple"
    val emoji: String   // Visual icon, e.g. "🍎"
)
```

### 📋 Why a Data Class?

Kotlin data classes automatically generate `equals()`, `hashCode()`, `toString()` and `copy()`. This means RecyclerView's `DiffUtil` can efficiently compare items for smart updates. The `data` keyword is the correct tool for simple value-carrying objects like list items.

---

## Section 4: Create the Item Layout

### STEP 4 — Create `item_fruit.xml` — The Row Layout

Design how each list item looks on screen.

1. Right-click **res/layout → New → Layout Resource File**
2. Name it `item_fruit`, root element `LinearLayout`, click **OK**
3. Switch to **Code** view and replace all content:

```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- res/layout/item_fruit.xml -->
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:gravity="center_vertical"
    android:padding="16dp"
    android:background="?attr/selectableItemBackground"
    android:clickable="true"
    android:focusable="true">

    <!-- Emoji icon -->
    <TextView
        android:id="@+id/tvEmoji"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:gravity="center"
        android:textSize="28sp" />

    <!-- Fruit name -->
    <TextView
        android:id="@+id/tvFruitName"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:layout_marginStart="16dp"
        android:textSize="18sp"
        android:textStyle="bold"
        android:textColor="@color/black" />

    <!-- Delete button -->
    <ImageButton
        android:id="@+id/btnDelete"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@android:drawable/ic_menu_delete"
        android:background="?attr/selectableItemBackgroundBorderless"
        android:contentDescription="Delete item" />

</LinearLayout>
```

### 📋 Layout Attribute Notes

- `android:background="?attr/selectableItemBackground"` — adds the ripple effect on tap.
- `android:layout_weight="1"` on `tvFruitName` — makes the name fill available horizontal space, pushing the delete button to the far right automatically.
- `android:clickable` and `android:focusable` — required for the ripple effect to activate.

---

## Section 5: Create the Adapter & ViewHolder

### STEP 5 — Create `FruitAdapter.kt`

The Adapter bridges your data list and the RecyclerView.

1. Right-click the package folder → **New → Kotlin Class/File**
2. Choose **Class** and name it `FruitAdapter`
3. Replace the entire file with the code below — read every comment:

```kotlin
package com.example.recyclerviewdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// ── 1. Extend RecyclerView.Adapter typed to our ViewHolder ───────────
class FruitAdapter(
    private val fruits: MutableList<Fruit>,
    private val onItemClick: (Fruit) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<FruitAdapter.FruitViewHolder>() {

    // ── 2. ViewHolder: holds view references for ONE item row ─────────
    inner class FruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmoji: TextView = itemView.findViewById(R.id.tvEmoji)
        val tvFruitName: TextView = itemView.findViewById(R.id.tvFruitName)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    // ── 3. onCreateViewHolder: inflate item_fruit.xml once per ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fruit, parent, false)
        return FruitViewHolder(view)
    }

    // ── 4. onBindViewHolder: fill a recycled ViewHolder with new data ──
    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        val fruit = fruits[position]
        // Populate the two TextViews
        holder.tvEmoji.text = fruit.emoji
        holder.tvFruitName.text = fruit.name

        // Whole-row click → invoke callback with the Fruit object
        holder.itemView.setOnClickListener {
            onItemClick(fruit)
        }

        // Delete button click → invoke callback with current position
        holder.btnDelete.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_ID.toInt()) {
                onDeleteClick(pos)
            }
        }
    }

    // ── 5. getItemCount: tells RecyclerView how many rows to draw ──────
    override fun getItemCount(): Int = fruits.size

    // ── 6. Helper: add a new item and notify RecyclerView ─────────────
    fun addFruit(fruit: Fruit) {
        fruits.add(fruit)
        notifyItemInserted(fruits.size - 1) // Efficient: animates the new row
    }

    // ── 7. Helper: remove an item at a given position ─────────────────
    fun removeFruit(position: Int) {
        if (position in fruits.indices) {
            fruits.removeAt(position)
            notifyItemRemoved(position) // Efficient: animates the removal
        }
    }
}
```

### 5.1 Understanding the Three Core Override Methods

| Method | When Called & What It Does |
|---|---|
| `onCreateViewHolder()` | Called when a NEW ViewHolder is needed. Inflates `item_fruit.xml` from XML into a View object, wraps it in a `FruitViewHolder`, and returns it. Called at most (screen height / item height) times. |
| `onBindViewHolder()` | Called every time a ViewHolder is about to become visible. Fills the ViewHolder's TextViews with data from `fruits[position]`. This is where recycling happens — the same ViewHolder object is refilled with different data. |
| `getItemCount()` | Called by RecyclerView to know how many rows to manage. Must always return the current size of your data list. If this returns 0, nothing is drawn. |

### 📋 Critical: Use `adapterPosition`, Not `position`

Inside a click listener, always read `holder.adapterPosition` — NOT the `position` parameter. If an item is removed with animation, the outer `position` variable can become stale before the click fires. `adapterPosition` always returns the current, accurate index at click time.

Also check: `if (pos != RecyclerView.NO_ID.toInt())` to guard against animation edge cases.

---

## Section 6: Design the Activity Layout

### STEP 6 — Edit `activity_main.xml` — The Main Screen

Add a RecyclerView plus controls to the Activity layout.

Open **res → layout → activity_main.xml**. Click **"Code"** in the editor toolbar and replace the entire file:

```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- res/layout/activity_main.xml -->
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="#F5F5F5">

    <!-- ── Title bar ──────────────────────────────── -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Fruit List"
        android:textSize="24sp"
        android:textStyle="bold"
        android:textColor="#1B5E20"
        android:background="#E8F5E9"
        android:padding="16dp"
        android:elevation="4dp" />

    <!-- ── Input row: EditText + Add button ──────── -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="12dp"
        android:gravity="center_vertical"
        android:background="#FFFFFF">

        <EditText
            android:id="@+id/etFruitName"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:hint="Enter fruit name"
            android:inputType="textCapSentences"
            android:maxLines="1" />

        <Button
            android:id="@+id/btnAdd"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Add"
            android:backgroundTint="#2E7D32"
            android:layout_marginStart="8dp" />

    </LinearLayout>

    <!-- ── Item count label ───────────────────────── -->
    <TextView
        android:id="@+id/tvItemCount"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="5 items"
        android:textSize="13sp"
        android:textColor="#757575"
        android:paddingHorizontal="16dp"
        android:paddingVertical="6dp" />

    <!-- ── RecyclerView — the main list ──────────── -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:clipToPadding="false"
        android:paddingBottom="16dp" />

</LinearLayout>
```

### 📋 `android:layout_weight="1"` on RecyclerView

Setting `layout_height` to `0dp` and `layout_weight` to `1` on the RecyclerView makes it expand to fill all remaining vertical space after the title bar and input row are drawn. This is the standard pattern for making a list fill the screen in a LinearLayout.

---

## Section 7: Wire Everything in MainActivity.kt

### STEP 7 — Write `MainActivity.kt` — The Controller

Connect the RecyclerView, Adapter, and all interaction logic.

Open **app → java → com.example.recyclerviewdemo → MainActivity.kt**. Replace the entire file:

```kotlin
package com.example.recyclerviewdemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // ── 1. Declare UI references ──────────────────────────────────────
    private lateinit var recyclerView: RecyclerView
    private lateinit var etFruitName: EditText
    private lateinit var btnAdd: Button
    private lateinit var tvItemCount: TextView
    private lateinit var adapter: FruitAdapter

    // ── 2. Seed data — the initial list ──────────────────────────────
    private val fruitList: MutableList<Fruit> = mutableListOf(
        Fruit("Apple", "🍎"),
        Fruit("Banana", "🍌"),
        Fruit("Cherry", "🍒"),
        Fruit("Mango", "🥭"),
        Fruit("Pineapple", "🍍")
    )

    // ── 3. onCreate ───────────────────────────────────────────────────
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 3a. Bind views
        recyclerView = findViewById(R.id.recyclerView)
        etFruitName = findViewById(R.id.etFruitName)
        btnAdd = findViewById(R.id.btnAdd)
        tvItemCount = findViewById(R.id.tvItemCount)

        // 3b. Create the Adapter with lambda callbacks
        adapter = FruitAdapter(
            fruits = fruitList,
            onItemClick = { fruit -> onFruitClicked(fruit) },
            onDeleteClick = { position -> onDeleteClicked(position) }
        )

        // 3c. Configure RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 3d. Initial count label
        updateItemCount()

        // 3e. Add button listener
        btnAdd.setOnClickListener { addFruit() }
    }

    // ── 4. Handle whole-row tap ───────────────────────────────────────
    private fun onFruitClicked(fruit: Fruit) {
        Toast.makeText(
            this,
            "${fruit.emoji} You tapped: ${fruit.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    // ── 5. Handle delete button tap ───────────────────────────────────
    private fun onDeleteClicked(position: Int) {
        val removed = fruitList[position]
        adapter.removeFruit(position)
        updateItemCount()
        Toast.makeText(
            this,
            "Removed: ${removed.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    // ── 6. Add a new fruit from the EditText ──────────────────────────
    private fun addFruit() {
        val name = etFruitName.text.toString().trim()
        if (name.isEmpty()) {
            etFruitName.error = "Please enter a fruit name"
            return
        }
        // Add the fruit to the list and notify the adapter
        adapter.addFruit(Fruit(name, "🍎"))
        updateItemCount()
        // Clear input and scroll to newly added item
        etFruitName.setText("")
        recyclerView.scrollToPosition(fruitList.size - 1)
        Toast.makeText(this, "Added: $name", Toast.LENGTH_SHORT).show()
    }

    // ── 7. Update the item count label ────────────────────────────────
    private fun updateItemCount() {
        val count = fruitList.size
        tvItemCount.text = "$count ${if (count == 1) "item" else "items"}"
    }
}
```

---

## Section 8: Code Walkthrough

### 8.1 LayoutManager

The line `recyclerView.layoutManager = LinearLayoutManager(this)` is mandatory. Without a LayoutManager, RecyclerView throws an exception and shows nothing. `LinearLayoutManager` arranges items top-to-bottom by default. For a horizontal list, pass `LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)`.

### 8.2 Lambda Callbacks vs. Interface

The Adapter accepts `onItemClick: (Fruit) -> Unit` and `onDeleteClick: (Int) -> Unit` as constructor parameters. This Kotlin lambda pattern is cleaner than creating a separate Listener interface — the Activity passes its handler functions directly, keeping all interaction logic inside `MainActivity` rather than spreading it across multiple files.

```kotlin
// Activity passes handler lambdas at construction time:
adapter = FruitAdapter(
    fruits = fruitList,
    onItemClick = { fruit -> onFruitClicked(fruit) },   // lambda
    onDeleteClick = { position -> onDeleteClicked(position) } // lambda
)

// Adapter stores and calls them:
holder.itemView.setOnClickListener {
    onItemClick(fruit) // Calls the lambda from MainActivity
}
```

### 8.3 `notifyItemInserted` vs. `notifyDataSetChanged`

When adding or removing items, always prefer the specific notify methods:

| Method | Scope | Result |
|---|---|---|
| `notifyItemInserted(pos)` | Single item | Plays slide-in animation; efficient |
| `notifyItemRemoved(pos)` | Single item | Plays slide-out animation; efficient |
| `notifyItemChanged(pos)` | Single item | Rebinds only that row; efficient |
| `notifyDataSetChanged()` | Entire list | Redraws all visible items; no animation; expensive — **avoid** |

### 8.4 `scrollToPosition()`

After adding a new item, `recyclerView.scrollToPosition(fruitList.size - 1)` instantly moves the list to reveal the newly added row at the bottom. For a smooth animated scroll, use `recyclerView.smoothScrollToPosition(...)` instead.

### 8.5 `MutableList` vs. `List`

The `fruitList` is declared as `MutableList<Fruit>` because the user can add and remove items at runtime. `listOf()` creates a read-only list — mutations would throw an `UnsupportedOperationException`. Always use `mutableListOf()` for RecyclerView data sources that change.

---

## Section 9: Run and Test

### STEP 8 — Build and Run

Press **Shift+F10** (or the green Run button) to deploy the app.

Follow this complete test sequence to verify every feature:

| # | Test Action | Expected Result | What It Tests |
|---|---|---|---|
| 1 | Launch the app | 5 fruits displayed in a scrollable list; item count shows '5 items' | RecyclerView initialisation + Adapter binding |
| 2 | Tap any fruit row (e.g. Banana) | Toast shows '🍌 You tapped: Banana' | `onItemClick` callback + ViewHolder click listener |
| 3 | Tap the delete button on Apple | Apple disappears with animation; count updates to '4 items'; Toast confirms removal | `notifyItemRemoved()` + animation |
| 4 | Type 'Watermelon' in the EditText and tap Add | Watermelon 🍎 appears at the bottom; list scrolls to it; count updates | `addFruit()` + `notifyItemInserted()` + `scrollToPosition()` |
| 5 | Tap Add with an empty EditText | Error message appears under the field — nothing added | Input validation guard |
| 6 | Delete all items one by one | List becomes empty; count shows '0 items' | MutableList shrinks; `getItemCount()` returns 0 |
| 7 | Rotate the device (portrait ↔ landscape) | List content remains the same | RecyclerView survives configuration change |
| 8 | Add 20+ items by repeatedly tapping Add | List scrolls smoothly with no lag | RecyclerView recycling mechanism working correctly |

---

## Section 10: Troubleshooting

| Error / Symptom | Likely Cause | Fix |
|---|---|---|
| RecyclerView shows nothing | `layoutManager` not set | Add: `recyclerView.layoutManager = LinearLayoutManager(this)` before setting adapter |
| NullPointerException on adapter | `setContentView()` called after `findViewById()` | Always call `setContentView(R.layout.activity_main)` first in `onCreate()` |
| Crash: Cannot resolve symbol 'RecyclerView' | Dependency not added to `build.gradle` | Add `implementation 'androidx.recyclerview:recyclerview:1.3.2'` and sync |
| Item click fires at wrong index after delete | Using outer `position` instead of `adapterPosition` | Always use `holder.adapterPosition` inside click listeners |
| List updates but no animation | Called `notifyDataSetChanged()` instead of `notifyItemInserted/Removed` | Replace with specific notify methods for smooth animated updates |
| Error inflating `item_fruit.xml` | Typo in layout filename or ID mismatch | Verify `R.layout.item_fruit` matches the XML file name exactly |
| ImageButton delete icon not showing | `android:src` drawable name not found | Use `@android:drawable/ic_menu_delete` or add a custom vector drawable |
| EditText error not clearing on next tap | `error` property set but never cleared | Set `etFruitName.error = null` before validation or on text change listener |

---

## Section 11: Advanced Enhancements

### Enhancement A — Add Item Dividers

Add a horizontal line between every item with one line in `MainActivity`:

```kotlin
import androidx.recyclerview.widget.DividerItemDecoration

// Add this after setting the layoutManager:
recyclerView.addItemDecoration(
    DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
)
```

### Enhancement B — Grid Layout

Switch from a vertical list to a 2-column grid by changing only the LayoutManager:

```kotlin
import androidx.recyclerview.widget.GridLayoutManager

// Replace LinearLayoutManager with:
recyclerView.layoutManager = GridLayoutManager(
    this,
    2 // Number of columns
)

// No changes needed in the Adapter!
```

### Enhancement C — Swipe-to-Delete with ItemTouchHelper

Allow users to swipe left on any item to delete it — no button required:

```kotlin
import androidx.recyclerview.widget.ItemTouchHelper

val swipeCallback = object : ItemTouchHelper.SimpleCallback(
    0,                          // Drag directions: none
    ItemTouchHelper.LEFT        // Swipe direction: left
) {
    override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        onDeleteClicked(position)
    }
}
ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView)
```

### Enhancement D — DiffUtil for Large Lists

When data changes significantly (e.g. loading from a network), use `DiffUtil` instead of `notifyDataSetChanged()` for efficient, animated updates:

```kotlin
import androidx.recyclerview.widget.DiffUtil

class FruitDiffCallback(
    private val old: List<Fruit>,
    private val new: List<Fruit>
) : DiffUtil.Callback() {
    override fun getOldListSize() = old.size
    override fun getNewListSize() = new.size
    override fun areItemsTheSame(oldPos: Int, newPos: Int) =
        old[oldPos].name == new[newPos].name
    override fun areContentsTheSame(oldPos: Int, newPos: Int) =
        old[oldPos] == new[newPos]
}

// Usage in Adapter — replaces the entire list intelligently:
fun updateList(newFruits: List<Fruit>) {
    val diff = DiffUtil.calculateDiff(FruitDiffCallback(fruits, newFruits))
    fruits.clear()
    fruits.addAll(newFruits)
    diff.dispatchUpdatesTo(this) // Plays minimal animations
}
```

---

## Section 12: Project File Summary

| File Path | Purpose | Key Content |
|---|---|---|
| `app/build.gradle` | Build config | RecyclerView dependency |
| `app/src/main/java/.../Fruit.kt` | Data model | `data class Fruit(name, emoji)` |
| `app/src/main/java/.../FruitAdapter.kt` | Adapter + ViewHolder | `onCreateViewHolder`, `onBindViewHolder`, `getItemCount` |
| `app/src/main/java/.../MainActivity.kt` | Activity controller | RecyclerView setup, Adapter wiring, click callbacks |
| `app/src/main/res/layout/activity_main.xml` | Main screen layout | RecyclerView, EditText, Button, item count label |
| `app/src/main/res/layout/item_fruit.xml` | List row layout | `tvEmoji`, `tvFruitName`, `btnDelete` |

---

*Android RecyclerView Experiment | Kotlin | Simple Scrollable List*
