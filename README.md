"# EngineerProCourseCode" 
Remove "(khoahochatde.com)":
Get-ChildItem -Recurse | Rename-Item -NewName { $_.Name -replace " \(khoahochatde\.com\)", "" }
Get-ChildItem -Recurse -Filter "Must read.txt" | Remove-Item -Force