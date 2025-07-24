@echo off
title CompileLatestClient

curl -f -L --output client.jar https://piston-data.mojang.com/v1/objects/0983f08be6a4e624f5d85689d1aca869ed99c738/client.jar
curl -f -L --output 1.8.json https://launchermeta.mojang.com/v1/packages/f6ad102bcaa53b1a58358f16e376d548d44933ec/1.8.json
curl -f -L --output mcp918.zip https://github.com/leijurv/MineBot/raw/refs/heads/master/mcp918.zip

echo All files downloaded successfully.
@pause
