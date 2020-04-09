# Toggleable Radio
Radio with toggleable state and configurable listeners.

### DEMO
![radiogroup](sample/radiogroup.gif?raw=true "Toggleable Radio")

### USAGE DEMO ON XML
    <you.thiago.toggleableradio.ToggleableRadioGroup
        android:id="@+id/toggleableRadio"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_margin="16dp">

        <you.thiago.toggleableradio.ToggleableRadioButton
            android:id="@+id/rbFirstState"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:drawablePadding="5dp"
            android:text="First State" />

        <you.thiago.toggleableradio.ToggleableRadioButton
            android:id="@+id/rbSecondState"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:drawablePadding="5dp"
            android:text="Second State" />

    </you.thiago.toggleableradio.ToggleableRadioGroup>

### Requirements
    Min SDK Version >= 19

### Import library from Jitpack
    - Add Jitpack repository into you project (build.gradle):

        allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }

    - Add library implementation into build.gradle (Module:app)

        dependencies {
            ...
            implementation 'com.github.thiago-you:toggleable-radio:Tag'
        }

    - Sync build.gradle and build your project

See [Jitpack](https://jitpack.io/docs/) docs for more info.

### Download Library
Follow these steps to import the library into your project:

    - Download the library
    - Go to you project under "File" -> "New" -> "Import Module"
    - In build.gradle, import library as "implementation project(':toggleable-radio')"
    - Sync build.gradle and build your project