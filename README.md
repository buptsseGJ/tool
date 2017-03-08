### 各功能使用注意事项</br></br>
#### 1.&nbsp;【程序脚本】-->【运行脚本】功能
##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用本功能前，需要自行编辑可执行脚本。	具体格式及示例如下：	
		   格式:  [测试用例名]   java  -cp  calfuzzer编译后class存放的路径  待执行用例的mian函数对应的类 
		   示例:  [测试用例名2]  java  -cp  C:/calfuzzer2/calfuzzer/classes   benchmarks.testcases.TestRace2
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;【注意】当前版本为逐行读取，故一句命令必须写在一行、不得断行；一行只能写一句命令，否则无法识别。<br />
#### 2.&nbsp;其他功能描述将补充
