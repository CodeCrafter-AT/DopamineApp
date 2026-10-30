import sys

content = open("app/src/test/java/com/example/GreetingScreenshotTest.kt").read()

content = content.replace('com.example.ui.components.LuxuryHeader(0, \\"Name\\", \\"Tier\\", 0, {}, {})', 'com.example.ui.components.LuxuryHeader(0, "Name", "Tier", 0, {}, {})')
content = content.replace("import com.example.ui.components.Header", "import com.example.ui.components.LuxuryHeader")

open("app/src/test/java/com/example/GreetingScreenshotTest.kt", "w").write(content)
