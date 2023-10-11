#!/bin/bash

directory="/home/sullivan/Insync/eu@marcussullivan.com/OneDrive"  # Substitua pelo caminho do seu diretório

find "$directory" -type f -mtime -7


#find /home/sullivan/Insync/eu@marcussullivan.com/OneDrive -type f -exec ls -l {} \;
