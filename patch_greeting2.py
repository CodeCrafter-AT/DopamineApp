import sys

content = open("app/src/test/java/com/example/GreetingScreenshotTest.kt").read()
content = content.replace("AppTheme", "NoirTheme")
open("app/src/test/java/com/example/GreetingScreenshotTest.kt", "w").write(content)
