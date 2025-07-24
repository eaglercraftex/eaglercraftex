@echo off
title Download Files

:: --- Config ---
set "SEVENZIP_URL=https://www.7-zip.org/a/7za920.zip"
set "SEVENZIP_ZIP=7za920.zip"
set "SEVENZIP_EXE_TEMP=7z_temp\7za.exe"
set "FFMPEG_URL=https://github.com/BtbN/FFmpeg-Builds/releases/download/latest/ffmpeg-master-latest-win64-gpl.zip"
set "FFMPEG_ZIP=ffmpeg_latest.zip"
set "FFMPEG_EXTRACT_TEMP=ffmpeg_extracted_temp"
set "FINAL_FFMPEG_FOLDER=ffmpeg"
set "CLIENT_URL=https://piston-data.mojang.com/v1/objects/0983f08be6a4e624f5d85689d1aca869ed99c738/client.jar"
set "JSON_URL=https://launchermeta.mojang.com/v1/packages/f6ad102bcaa53b1a58358f16e376d548d44933ec/1.8.json"
set "MCP_URL=https://github.com/leijurv/MineBot/raw/refs/heads/master/mcp918.zip"


:: --- Create a temporary directory for 7-Zip ---
if not exist "7z_temp\" mkdir "7z_temp\"

echo.
echo Step 1: Downloading portable 7-Zip (7za.exe)...
curl -f -L --output "%SEVENZIP_ZIP%" "%SEVENZIP_URL%"
if errorlevel 1 (
    echo ERROR: Failed to download 7-Zip. Check URL or internet.
    goto :cleanup
)
echo Download complete: %SEVENZIP_ZIP%

echo.
echo Step 2: Extracting 7za.exe...
:: Use Windows' built-in zip extraction
powershell -Command "Expand-Archive -Path '%SEVENZIP_ZIP%' -DestinationPath '7z_temp' -Force"
if errorlevel 1 (
    echo ERROR: Failed to extract 7za.exe using PowerShell.
    goto :cleanup
)
echo Extracted 7za.exe to 7z_temp\

:: --- Check for existing FFmpeg folder ---
if exist "%FINAL_FFMPEG_FOLDER%\" (
    echo WARNING: A folder named "%FINAL_FFMPEG_FOLDER%" already exists.
    echo Please move or delete it before running this script.
    goto :cleanup
)

echo.
echo Step 3: Downloading FFmpeg...
curl -f -L --output "%FFMPEG_ZIP%" "%FFMPEG_URL%"
if errorlevel 1 (
    echo ERROR: Failed to download FFmpeg. Check URL or internet.
    goto :cleanup
)
echo Download complete: %FFMPEG_ZIP%

echo.
echo Step 4: Extracting FFmpeg using 7za.exe...
mkdir "%FFMPEG_EXTRACT_TEMP%"
"%SEVENZIP_EXE_TEMP%" x "%FFMPEG_ZIP%" -o"%FFMPEG_EXTRACT_TEMP%\" -y
if errorlevel 1 (
    echo ERROR: Failed to extract FFmpeg using 7za.exe. Make sure 7za.exe is valid.
    goto :cleanup
)
echo Extraction complete to %FFMPEG_EXTRACT_TEMP%\

echo.
echo Step 5: Moving FFmpeg contents and cleaning up...
:: Find the actual FFmpeg folder inside the extracted one (e.g., ffmpeg-master-latest-win64-gpl)
for /d %%i in ("%FFMPEG_EXTRACT_TEMP%\ffmpeg-*") do (
    ren "%%i" "%FINAL_FFMPEG_FOLDER%"
    if errorlevel 1 (
        echo ERROR: Failed to rename extracted FFmpeg folder.
        goto :cleanup
    )
)

:: Move ffmpeg.exe, ffprobe.exe, ffplay.exe up one level for easier access
if exist "%FINAL_FFMPEG_FOLDER%\bin\" (
    move /y "%FINAL_FFMPEG_FOLDER%\bin\ffmpeg.exe" "%FINAL_FFMPEG_FOLDER%\" >nul 2>nul
    move /y "%FINAL_FFMPEG_FOLDER%\bin\ffprobe.exe" "%FINAL_FFMPEG_FOLDER%\" >nul 2>nul
    move /y "%FINAL_FFMPEG_FOLDER%\bin\ffplay.exe" "%FINAL_FFMPEG_FOLDER%\" >nul 2>nul
    rmdir /s /q "%FINAL_FFMPEG_FOLDER%\bin\" >nul 2>nul
    echo Moved ffmpeg.exe, ffprobe.exe, ffplay.exe to the root of "%FINAL_FFMPEG_FOLDER%".
)

:cleanup
echo.
echo Cleaning up temporary files...
if exist "%SEVENZIP_ZIP%" del "%SEVENZIP_ZIP%"
if exist "%FFMPEG_ZIP%" del "%FFMPEG_ZIP%"
if exist "7z_temp\" rmdir /s /q "7z_temp\"
if exist "%FFMPEG_EXTRACT_TEMP%\" rmdir /s /q "%FFMPEG_EXTRACT_TEMP%\"
echo Cleanup complete.

curl -f -L --output client.jar %CLIENT_URL%
curl -f -L --output 1.8.json %JSON_URL%
curl -f -L --output mcp918.zip %MCP_URL%


pause
exit /b %errorlevel%
