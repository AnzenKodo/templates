#!/usr/bin/env bash

# If command fails, script will exit
set -o errexit
# Script will fail when accessing an unset variable
set -o nounset
# Pipeline command is treated as failed
set -o pipefail

# `TRACE=1 ./script.sh` can enable Debug mode.
if [[ "${TRACE-0}" == "1" ]]; then
    set -o xtrae;
fi

# Change script directory to command line path
cd "$(dirname "$0")"

func()
{
    local var
}
