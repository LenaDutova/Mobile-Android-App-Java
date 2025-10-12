# A three-window application has been developed for user login, registration, and data viewing.

## Design
The layout of the screens was done using XML. See res/layout
Added constant values in color, dimens and strings. See res/values (-night, -ru) 
String resources contain two localizations (RU, EN as default). Chose language on device or emulator to see the differences/
Added theme and styles (default, night)
Added custom LOGO-image


## Activity
Each activity (StartActivity, ReturningActivity, FinalActivity) is added to the file AndroidManifest.xml. One of them has been declared the starting one.
```
<intent-filter>
    <action android:name="android.intent.action.MAIN" />
    <category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
```


## Life cycle
Created parent Activity (DebuggingActivity) which is declared parental (extends) to others.


## Interaction
In Activity find views from XML with findViewById(). Change them data or add listeners: 
```
Button btn = (Button) findViewById(R.id.btn_id);
btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        // Click to button with ID
    }
});
```

Some listeners encourage start of another Activity by using explicit intent-object:
```
Intent intent = new Intent(this, AnotherActivity.class);
startActivity(intent);
finish();
```

Some listeners encourage start of Activity for returning some Result-data via launcher subscription:
```
Intent intent = new Intent(this, AnotherActivity.class);
resultLauncher.launch(intent);  // New way
// startActivityForResult(intent, REQUEST);  // @Deprecated way
```

After which it`s necessary to read the data from Bundle (in returned Intent) storing data as key-value pairs.
Child activity in turn must send the corresponding data:
```
Intent intent = new Intent();
intent.putExtra("KEY", "value");
setResult(RESULT_OK, intent);
finish();
```