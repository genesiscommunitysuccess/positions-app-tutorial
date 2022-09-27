#!/bin/bash
source /home/positions-app-tutorial/.bashrc
systemctl start postgresql-14
su -c "source /home/positions-app-tutorial/.bashrc ; yes | remap --commit" - "positions-app-tutorial"
su -c "JvmRun global.genesis.environment.scripts.SendTable -t USER -f /home/positions-app-tutorial/run/site-specific/data/user.csv" - "positions-app-tutorial"
echo "remap done"
