#!/bin/bash

# To update plugin version string in every depend file:
# change the version in plugin.xml
# run this script

# 1 Get plugin version from plugin.xml
# Define the path to the plugin.xml file
plugin_xml="plugin.xml"
# Extract the version string from the plugin.xml file
plugin_version=$(sed -n 's/.*version="\([^"]*\).*/\1/p' "$plugin_xml")
echo "Plugin version is: $plugin_version"

# 2 Update plugin version in iOS component
# Define the path to the file
file_path="src/ios/VerintXM.m"
# Use sed to replace the version string
sed -i "" "s/NSString\* const version = @\".*\";/NSString\* const version = @\"${plugin_version}\";/" "$file_path"
echo "iOS component version updated to ${plugin_version}"

# 3 Update plugin version in Android component
# Define the path to the file
file_path="src/android/VerintXM.java"
# Use sed to replace the version string
sed -i "" "s/private final String version = \".*\";/private final String version = \"${plugin_version}\";/" "$file_path"
echo "Android component version updated to ${plugin_version}"
