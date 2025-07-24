#!/bin/sh

curl -f -L --output client.jar https://piston-data.mojang.com/v1/objects/0983f08be6a4e624f5d85689d1aca869ed99c738/client.jar
curl -f -L --output 1.8.json https://launchermeta.mojang.com/v1/packages/f6ad102bcaa53b1a58358f16e376d548d44933ec/1.8.json
curl -f -L --output mcp918.zip https://github.com/leijurv/MineBot/raw/refs/heads/master/mcp918.zip

echo "All files downloaded successfully."

# FFmpeg Installation Script (Package Manager Only) for macOS and Linux

echo "Installing FFMPEG"

# Function to check if a command exists
command_exists () {
  type "$1" &> /dev/null ;
}

# --- Main Script Logic ---

# Check the operating system
OS="$(uname -s)"

case "${OS}" in
    Linux*)
        echo -e "\n--- Linux Installation via Package Manager ---"
        if command_exists apt-get; then
            echo "Detected Debian/Ubuntu. Installing FFmpeg using apt..."
            sudo apt-get update
            sudo apt-get install -y ffmpeg
        elif command_exists dnf; then
            echo "Detected Fedora/RHEL 8+. Installing FFmpeg using dnf..."
            sudo dnf install -y ffmpeg
        elif command_exists yum; then
            echo "Detected CentOS/RHEL 7-. Installing FFmpeg using yum..."
            sudo yum install -y epel-release # EPEL is often needed for FFmpeg on older RHEL/CentOS
            sudo yum install -y ffmpeg
        elif command_exists pacman; then
            echo "Detected Arch Linux. Installing FFmpeg using pacman..."
            sudo pacman -Sy ffmpeg
        else
            echo "Error: No supported package manager found (apt, dnf, yum, pacman)."
            exit 1
        fi
        ;;
    Darwin*)
        echo -e "\n--- macOS Installation via Homebrew ---"
        if ! command_exists brew; then
            echo "Homebrew is not installed. Installing Homebrew..."
            /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
            if [ $? -ne 0 ]; then
                echo "Error: Homebrew installation failed. Please try again manually."
                exit 1
            fi
        else
            echo "Homebrew is already installed."
        fi
        echo "Installing FFmpeg via Homebrew..."
        brew install ffmpeg
        ;;
    *)
        echo "Unsupported operating system: ${OS}"
        echo "This script is designed for macOS and Linux."
        exit 1
        ;;
esac

if [ $? -ne 0 ]; then
    echo "Error: FFmpeg installation via package manager failed."
    echo "Please check the output for details or try installing manually."
    exit 1
fi

echo -e "\n--- Verifying Installation ---"
if command_exists ffmpeg; then
    echo "FFmpeg installed successfully!"
    ffmpeg -version
else
    echo "FFmpeg does not appear to be installed or is not in your PATH."
    echo "Please check the installation logs for errors."
fi

echo "Finished!"
