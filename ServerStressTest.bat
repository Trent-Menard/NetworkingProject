@ECHO OFF
FOR /L %%B IN (1,1,10) DO (
    @ECHO Attempting Client Connection #%%B
    START "Client %%B" CMD /K "java -jar %CD%\TCPClient.jar"
    TIMEOUT /T 1 /NOBREAK
)
TIMEOUT /T 120
TASKKILL /F /IM cmd.exe /T