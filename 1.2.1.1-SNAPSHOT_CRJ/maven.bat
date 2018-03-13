@echo off 
rem 关闭自动输出

set "cmd1=call mvn deploy -Dmaven.test.skip=true"
set "cmd2=call mvn clean -Dskip_compress=false -Dmaven.test.skip=true release:clean  release:prepare  -Dresume=false -Pprod -Dskip_jetty=true -Dskip_doc=false -Dskip_tomcat=true "
set "cmd3=call mvn release:clean release:update-versions"
set "cmd4=call mvn release:clean release:branch -DupdateBranchVersions=true -DupdateWorkingCopyVersions=false"
set "cmd5=call mvn clean release:clean -Dmaven.test.skip=true "
set "cmd6=call mvn clean -Dmaven.test.skip=true -Plocalhost -Dskip_tomcat=false -Dskip_compress=false tomcat7:run"
set "cmd7=call mvn clean deploy -Dmaven.test.skip=true -Plocalhost -Dskip_compress=false -Djetty.skip=true"
set "cmd8=call mvn clean deploy -Dmaven.test.skip=true -Pprod -Dskip_compress=false -Djetty.skip=true"
set "cmd9=call mvn clean deploy -Dmaven.test.skip=true -Pdev198 -Dskip_compress=false -Djetty.skip=true"
set "cmd10=call mvn clean deploy -Dmaven.test.skip=true -Pdevsh -Dskip_compress=false -Djetty.skip=true"
set "cmd11=call mvn release:perform -Dmaven.test.skip=true -Djetty.skip=true"
set "cmd12=call mvn clean install -e -U -Dmaven.test.skip=true -Djetty.skip=true"

:startcmd

TITLE 命令行处理
echo=
echo 请选择你需要执行的命令
echo=
echo ------------------------------------------------------------------------------------------------
echo   *1、部分快速打包，默认为本地环境跳过单元测试：%cmd1%
echo=
echo   *2、准备发布的时候打包，次会自动进行版本号累加：%cmd2%
echo=
echo   *3、更新版本号： %cmd3%
echo=
echo   *4、开启分支：%cmd4%
echo=
echo   *5、清除maven文件信息：%cmd5%
echo=
echo   *6、启动本地tomcat服务(如需要重新加载程序直接输入回车符)：%cmd6%
echo=
echo   *7、本地环境打包，如本地测试数据库：%cmd7%
echo=
echo   *8、真实环境打包，如线上数据库：%cmd8%
echo=
echo   *9、198环境打包，如真实测试数据库：%cmd9%
echo=
echo   *10、上海环境打包，如真实测试数据库：%cmd10%
echo=
echo   *11、打包发布到私服：%cmd11%
echo=
echo   *12、强制重新下载jar包：%cmd12%
rem 接收输入
:begin
echo ------------------------------------------------------------------------------------------------
echo=
set input=
set /p input=请输入选择命令:

if "%input%" equ "" goto :begin
echo=
echo --------------------------------------------------------------------
echo=
:agenlog

set islog=
set /p islog=请输入是否打印日志到文件（1是/0否）[默认为0]:

if "%islog%" equ "" set islog=0
echo=
echo --------------------------------------------------------------------

rem  部分快速打包，默认为本地环境跳过单元测试：mvn deploy -Dmaven.test.skip=true
if %input% equ 1 goto :cmd1
if %input% equ 2 goto :cmd2
if %input% equ 3 goto :cmd3
if %input% equ 4 goto :cmd4
if %input% equ 5 goto :cmd5
if %input% equ 6 goto :cmd6
if %input% equ 7 goto :cmd7
if %input% equ 8 goto :cmd8
if %input% equ 9 goto :cmd9
if %input% equ 10 goto :cmd10
if %input% equ 11 goto :cmd111
if %input% equ 12 goto :cmd112


:cmd1
if %islog% equ 1 goto :cmdt11
if %islog% neq 1 goto :cmdt12
:cmdt11

echo ***************************************************************************
echo ************执行当中，请稍后...  %cmd1%
echo ***************************************************************************
%cmd1% > cmd.log

goto end
:cmdt12
%cmd1%

goto end



:cmd2
if %islog% equ 1 goto :cmdt21
if %islog% neq 1 goto :cmdt22
:cmdt21
echo ***************************************************************************
echo ************执行当中，请稍后... 执行命令为：%cmd2%
echo ***************************************************************************
%cmd2% > cmd.log

goto end
:cmdt22
%cmd2%

goto end

:cmd3
if %islog% equ 1 goto :cmdt31
if %islog% neq 1 goto :cmdt32
:cmdt31
echo ***************************************************************************
echo ************执行当中，请稍后... %cmd3%
echo ***************************************************************************
%cmd3%  > cmd.log

goto end
:cmdt32
%cmd3%

goto end

:cmd4


set input4=
set /p input4=请输入开启分支版本对应文件名，格式如《项目名称_项目当前版本_累加数字》:

if "%input4%" equ "" goto :cmd4

if %islog% equ 1 goto :cmdt41
if %islog% neq 1 goto :cmdt42
:cmdt41
echo ***************************************************************************
echo ************执行当中，请稍后... %cmd4%
echo ***************************************************************************
%cmd4% > cmd.log
goto end

:cmdt42
%cmd4% -DbranchName=%input4%

goto end

:cmd5
if %islog% equ 1 goto :cmdt51
if %islog% neq 1 goto :cmdt52
:cmdt51
echo ***************************************************************************
echo ************执行当中，请稍后...  %cmd5%
echo ***************************************************************************
%cmd5%  > cmd.log

goto end
:cmdt52
%cmd5% 

goto end

:cmd6
echo ***************************************************************************
echo ************正在启动服务，请稍后...  %cmd6%
echo ***************************************************************************
rem 定义启动服务的窗口名称
TITLE 后台管理平台  http://localhost:8180
if %islog% equ 1 goto :cmdt61
if %islog% neq 1 goto :cmdt62
:cmdt61
%cmd6% > cmd.log

goto end
:cmdt62
%cmd6% 

goto end

:cmd7
echo ***************************************************************************
echo ************正在启动服务，请稍后...  %cmd7%
echo ***************************************************************************

if %islog% equ 1 goto :cmdt71
if %islog% neq 1 goto :cmdt72
:cmdt71
%cmd7% > cmd.log

goto end
:cmdt72
%cmd7% 

goto end

:cmd8
echo ***************************************************************************
echo ************正在启动服务，请稍后...  %cmd8%
echo ***************************************************************************

if %islog% equ 1 goto :cmdt81
if %islog% neq 1 goto :cmdt82
:cmdt81
%cmd8% > cmd.log

goto end
:cmdt82
%cmd8% 

goto end

:cmd9
echo ***************************************************************************
echo ************正在打包，请稍后...  %cmd9%
echo ***************************************************************************

if %islog% equ 1 goto :cmdt91
if %islog% neq 1 goto :cmdt92
:cmdt91
%cmd9% > cmd.log

goto end
:cmdt92
%cmd9% 

goto end

:cmd10
echo ***************************************************************************
echo ************正在打包，请稍后...  %cmd10%
echo ***************************************************************************

if %islog% equ 1 goto :cmdt101
if %islog% neq 1 goto :cmdt102
:cmdt101
%cmd10% > cmd.log

goto end
:cmdt102
%cmd10% 

goto end

:cmd111
echo ***************************************************************************
echo ************正在打包发布到私服，请稍后...  %cmd11%
echo ***************************************************************************

if %islog% equ 1 goto :cmdt1111
if %islog% neq 1 goto :cmdt1112
:cmdt1111
%cmd11% > cmd.log

goto end
:cmdt1112
%cmd11% 

goto end

:cmd112
echo ***************************************************************************
echo ************正在打包发布到私服，请稍后...  %cmd12%
echo ***************************************************************************

if %islog% equ 1 goto :cmdt1121
if %islog% neq 1 goto :cmdt1122
:cmdt1121
%cmd12% > cmd.log

goto end
:cmdt1122
%cmd12% 

goto end

:end
echo ***************************************************************************
echo ************执行结束 
echo ***************************************************************************

set agenis=
set /p agenis=是否需要继续执行其他命令？（1是/0否）[默认为0]:

if "%agenis%" equ "" set agenis=0
if %agenis% equ 1 goto :startcmd
