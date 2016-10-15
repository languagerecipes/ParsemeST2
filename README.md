# ParsemeST.jar
Codes for checking TSV Annotaiton files and computing IAA. 


# How to use this code? 
* Make sure that you have the latest version of Java Runtime Machine is installed on your computer.  

Download ParsemeST.jar from https://github.com/languagerecipes/ParsemeST2/blob/master/bin/ParsemeST.jar?raw=true
 * If you need, an example of annotation file also download: https://github.com/languagerecipes/ParsemeST2/raw/master/bin/Pilot2%20ST%20-%20Template.tsv 

To run the codes, please go to your OS terminal (for windows users, please type "cmd" in the "run or search box" of your "start button" and then click on "Command Prompt"). Type the following in the terminal:

$ ```java -jar ParsemeST.jar arg1 arg2 (and opptionally arg3 and 4)```

instead of "arg1" and "arg2" in the above, use the following values:

Obligatory input arguments `Arg1` and `Arg2` :
	Arg1) path for the first annotation file
	Arg2) path for the second annotation file
if the paths contains a space-char, then please use quotation marks around them,
 * e.g., ```"Pilot ST - French - Agata Savary.tsv"```.

Optionally, you can ask for some more using these arguments after the provided `Arg1` and `Arg2`:
	Arg3) verbose report by asserting the word verbose in front of the file names 
	Arg4) ask for restricting your annotation vocabulary by this argument:  `Vocab:Your-annotation-vocab-file`,
 * e.g., `Vocab:english-file.txt` in which the english-file.txt is the file that contains your vocab,
		 e.g., LVC, ID, etc.

You can access this instructions simply by typing:
$ ```java -jar ParsemeST.jar```
		 
## About the vocab file
Please create a text file and and write each of the types separated by spaces or new lines.

# How to check only one file?
*If you like to check only one file, for the first `Arg1` and `Arg2` referring to the same file.
 * e.g., ```"Pilot ST - French - Agata Savary.tsv" "Pilot ST - French - Agata Savary.tsv"``` 

# Example usage:

For instanc running the code over the provided example file, with the arguments ```"Pilot2 ST - Template.tsv" "Pilot2 ST - Template.tsv"``` generates this output:

$ ```java -jar ParsemeST.jar "Pilot2 ST - Template.tsv" "Pilot2 ST - Template.tsv"```


No annotation type is given: your file is parsed without any restriction on their types.
You have requested a check on Pilot2 ST - Template.tsv
No annotation type is given: your file is parsed without any restriction on their types.
Parsing your annotation file...
If there are errors the parser will throw you an Exception! 
ID accepted: number of instances are 3
ID/_ accepted: number of instances are 1
LVC accepted: number of instances are 1
LVC/ID accepted: number of instances are 2
LVC/_ accepted: number of instances are 1
OTH accepted: number of instances are 1
VPC accepted: number of instances are 3
Your file seems to be ok! Here are some stat for you: 
--- annotation profile for ->  Pilot2 ST - Template.tsv
Number of annotations: 12
Sentenc: 8
Token: 105
Average Number of annotated VMWE per Sentence: 1.5
MWEs with Identical Surface Structure (i.e., only strings and not types): 12
If you have passed the -verbose parameter in your command, then further information and the list of MWEs can be found in Pilot2 ST - Template.tsv-MWEList.txt

# Bugs and Issues
Please report bugs and issues using Github: https://github.com/languagerecipes/ParsemeST2/issues 
