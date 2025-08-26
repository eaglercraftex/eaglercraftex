#!/bin/sh
set -e

if command -v curl >/dev/null 2>&1; then
  DL_CMD="curl -fL -o"
elif command -v wget >/dev/null 2>&1; then
  DL_CMD="wget -O"
else
  echo "Neither curl nor wget is installed. Please install one and try again."
  exit 1
fi

# Download files
$DL_CMD client.jar https://piston-data.mojang.com/v1/objects/0983f08be6a4e624f5d85689d1aca869ed99c738/client.jar
$DL_CMD 1.8.json https://launchermeta.mojang.com/v1/packages/f6ad102bcaa53b1a58358f16e376d548d44933ec/1.8.json
$DL_CMD mcp918.zip https://github.com/leijurv/MineBot/raw/refs/heads/master/mcp918.zip

echo "All files downloaded successfully."