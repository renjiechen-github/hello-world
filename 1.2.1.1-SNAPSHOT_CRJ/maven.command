#!/bin/bash

CMD_PATH="$( dirname "$0" )"
cd "$CMD_PATH"

cmd1="mvn deploy -Dmaven.test.skip=true"
cmd2="mvn clean release:clean release:prepare -Dresume=false -Pprod -Dskip_jetty=true -Dskip_doc=false -Dskip_tomcat=true "
cmd3="mvn release:clean release:update-versions"
cmd4="mvn release:clean release:branch -DupdateBranchVersions=true -DupdateWorkingCopyVersions=false"
cmd5="mvn clean release:clean"
cmd6="mvn clean -Dmaven.test.skip=true -Plocalhost -Dskip_compress=false -Dskip_tomcat=false tomcat7:run"
cmd7="mvn clean deploy -Dmaven.test.skip=true -Plocalhost -Dskip_compress=false -Djetty.skip=true"
cmd8="mvn clean deploy -Dmaven.test.skip=true -Pprod -Dskip_compress=false -Djetty.skip=true"
cmd9="mvn clean deploy -Dmaven.test.skip=true -Pdev198 -Dskip_compress=false -Djetty.skip=true"
cmd10="mvn clean deploy -Dmaven.test.skip=true -Pdevsh -Dskip_compress=false -Djetty.skip=true"
cmd11="mvn release:perform -Dmaven.test.skip=true -Djetty.skip=true"
cmd12="mvn clean install -e -U -Dmaven.test.skip=true -Djetty.skip=true"

echo ""
echo -n "请选择你需要执行的命令"
echo ""
echo ""
echo   "*1、部分快速打包，默认为本地环境跳过单元测试："$cmd1
echo	""
echo   "*2、准备发布的时候打包，次会自动进行版本号累加："$cmd2
echo	""
echo   "*3、更新版本号： "$cmd3
echo	""
echo   "*4、开启分支："$cmd4
echo	""
echo   "*5、清除maven文件信息："$cmd5
echo	""
echo   "*6、启动本地tomcat服务(如需要重新加载程序直接输入回车符)："$cmd6
echo	""
echo   "*7、本地环境打包，如本地测试数据库："$cmd7
echo	""
echo   "*8、真实环境打包，如线上数据库："$cmd8
echo	""
echo   "*9、198环境打包，如真实测试数据库："$cmd9
echo	""
echo   "*10、上海环境打包，如真实测试数据库："$cmd10
echo	""
echo   "*11、打包发布到私服："$cmd11
echo	""
echo   "*12、强制重新下载jar包："$cmd12
echo   ""
echo -n "请输入选择命令:"
read input

if [ "$input" = "1" ]; then
	$cmd1
elif [ "$input" = "2" ]; then
	$cmd2
elif [ "$input" = "3" ]; then
	$cmd3
elif [ "$input" = "4" ]; then 
	$cmd4
elif [ "$input" = "5" ]; then 
	$cmd5
elif [ "$input" = "6" ]; then 
	$cmd6
elif [ "$input" = "7" ]; then 
	$cmd7
elif [ "$input" = "8" ]; then 
	$cmd8
elif [ "$input" = "9" ]; then 
	$cmd9
elif [ "$input" = "10" ]; then 
	$cmd10
elif [ "$input" = "11" ]; then 
	$cmd11
else
	$cmd12
fi

echo 按任意键继续
read -n 1
