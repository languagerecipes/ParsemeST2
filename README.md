# ParsemeST.jar
Codes for checking TSV Annotaiton files and computing IAA. 


# How to obtain and use this code? 
* Make sure that you have the latest version of Java Runtime Machine is installed on your computer.  

Download the code from https://github.com/languagerecipes/ParsemeST2/raw/master/bin.zip and unzip it in a local folder.
 * If you need, an example of annotation file also download: https://github.com/languagerecipes/ParsemeST2/raw/master/bin/Pilot2%20ST%20-%20Template.tsv 

Alternatively, use the git commands to downlad and access files. Binaries can be found in the `bin.zip`. For example, clone this repo by:

$ ```git clone https://github.com/languagerecipes/ParsemeST2.git```

# How to run this code? 
To run the codes, please go to your OS terminal (for windows users, please type "cmd" in the "run or search box" of your "start button" and then click on "Command Prompt"). Type the following in the terminal:

$ ```java -jar ParsemeST.jar arg1 arg2 arg3 etc.```

instead of "arg1" and "arg2" in the above, use the following values:

## To check and validate an annotation file: 
To validate an annotation file, you have to provide two obligatory input arguments:

 Arg1) specify the language by typing: ```lang:INPUT_LANGUAGE```, where ```INPUT_LANGUAGE``` can be, e.g., English, Polish, Turkish, etc.
 Arg2) path for the annotation file by typing: ```source:ANNOTATION_FILE_PATH```, where ```ANNOTATION_FILE_PATH``` is the path to your TSV file. If the paths contains a space-char, then please use quotation marks around them,
 * e.g., ```source:"Pilot ST - French - Agata Savary.tsv"```.

## To compute IAA: 

 Arg1) specify the language by typing: ```lang:INPUT_LANGUAGE```, where ```INPUT_LANGUAGE``` can be, e.g., English, Polish, Turkish, etc.
 Arg2) path for the first annotation file by typing: ```source:ANNOTATION_FILE_PATH```, where ```ANNOTATION_FILE_PATH``` is the path to your TSV file. If the paths contains a space-char, then please use quotation marks around them, e.g., ```source:"Pilot ST - French - Agata Savary.tsv"```.
 Arg3) path for the second annotation file by typing: ```target:ANNOTATION_FILE_PATH```, where ```ANNOTATION_FILE_PATH``` is the path to your TSV file. * e.g., ```target:"Pilot ST - French - John Kennedy.tsv"```.

## Optional Arguments: 
Optionally, you can ask for a verbose report by typing ```verbose``` as an argument in the command line. 

You can access these instructions simply by typing:
$ ```java -jar ParsemeST.jar```
		 
# Example usage:

For instanc running the code over the provided example file, with the arguments ```"Pilot2 ST - Template.tsv" "Pilot2 ST - Template.tsv"``` generates this output:

$ ```java -jar ParsemeST.jar lang:English source:"Pilot2 ST - Template.tsv"```


```
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
```

# Bugs and Issues
Please report bugs and issues using Github: https://github.com/languagerecipes/ParsemeST2/issues 
