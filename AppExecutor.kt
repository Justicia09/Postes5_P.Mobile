<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.core.widget.NestedScrollView
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">
            <TextView
                android:id="@+id/tv_app_title"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="INSGAGRAM"
                android:textSize="18sp"
                android:textStyle="bold"
                android:textColor="@android:color/black"
                android:paddingStart="16dp"
                android:paddingEnd="16dp"
                android:paddingTop="12dp"
                android:paddingBottom="8dp" />

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/rv_stories"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingVertical="8dp"
                android:clipToPadding="false"
                android:orientation="horizontal"
                app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                tools:listitem="@layout/item_story" />

            <View
                android:layout_width="match_parent"
                android:layout_height="1dp"
                android:background="#E0E0E0"/>

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/rv_postingan"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:nestedScrollingEnabled="false"
                app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
                tools:listitem="@layout/item_postingan" />

        </LinearLayout>
    </androidx.core.widget.NestedScrollView>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab_add"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:src="@drawable/person_add_24"
        android:contentDescription="Tambah Postingan"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
