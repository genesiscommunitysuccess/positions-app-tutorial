#!/bin/bash
source /home/positions-app-tutorial/.bashrc
systemctl start postgresql-14
su -c "source /home/positions-app-tutorial/.bashrc ; genesisInstall" - "positions-app-tutorial"
echo "genesisInstall done"