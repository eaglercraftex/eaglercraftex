@echo off
title Download files

where curl >nul 2>nul
if errorlevel 1 (
    where wget >nul 2>nul
    if errorlevel 1 (
        echo Neither curl nor wget is installed. Please install one and try again.
        exit /b 1
    )
    set "DL_CMD=wget -O"
    set "USE_WGET=1"
) else (
    set "DL_CMD=curl -f -L --output"
    set "USE_WGET=0"
)

if "%USE_WGET%"=="1" (
    %DL_CMD% client.jar https://piston-data.mojang.com/v1/objects/0983f08be6a4e624f5d85689d1aca869ed99c738/client.jar || exit /b 1
    %DL_CMD% 1.8.json https://launchermeta.mojang.com/v1/packages/f6ad102bcaa53b1a58358f16e376d548d44933ec/1.8.json || exit /b 1
    %DL_CMD% mcp918.zip https://github.com/leijurv/MineBot/raw/refs/heads/master/mcp918.zip || exit /b 1
) else (
    %DL_CMD% client.jar https://piston-data.mojang.com/v1/objects/0983f08be6a4e624f5d85689d1aca869ed99c738/client.jar || (
        where wget >nul 2>nul
        if not errorlevel 1 (
            wget -O client.jar https://piston-data.mojang.com/v1/objects/0983f08be6a4e624f5d85689d1aca869ed99c738/client.jar || exit /b 1
        ) else (
            exit /b 1
        )
    )
    %DL_CMD% 1.8.json https://launchermeta.mojang.com/v1/packages/f6ad102bcaa53b1a58358f16e376d548d44933ec/1.8.json || (
        where wget >nul 2>nul
        if not errorlevel 1 (
            wget -O 1.8.json https://launchermeta.mojang.com/v1/packages/f6ad102bcaa53b1a58358f16e376d548d44933ec/1.8.json || exit /b 1
        ) else (
            exit /b 1
        )
    )
    %DL_CMD% mcp918.zip https://github.com/leijurv/MineBot/raw/refs/heads/master/mcp918.zip || (
        where wget >nul 2>nul
        if not errorlevel 1 (
            wget -O mcp918.zip https://github.com/leijurv/MineBot/raw/refs/heads/master/mcp918.zip || exit /b 1
        ) else (
            exit /b 1
        )
    )
)

echo All files downloaded successfully.
@pause