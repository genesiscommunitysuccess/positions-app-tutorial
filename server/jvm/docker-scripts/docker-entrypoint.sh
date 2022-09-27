#!/bin/bash
systemctl start postgresql-14
systemctl enable sshd.service
systemctl start sshd.service
su -c "startServer" - "positions-app-tutorial"
echo "Logged as positions-app-tutorial, starting server"
tail -f /dev/null