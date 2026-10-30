import sys

content = open("app/src/test/java/com/example/GreetingScreenshotTest.kt").read()

content = content.replace('Header({}, {}, {}, 0)', 'com.example.ui.components.Header({}, {}, {}, 0)')

open("app/src/test/java/com/example/GreetingScreenshotTest.kt", "w").write(content)
