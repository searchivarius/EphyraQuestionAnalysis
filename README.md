EphyraQuestionAnalysis
======================

A collection of [OpenEphyra](http://sourceforge.net/projects/openephyra/) components necessary for question analysis. **Dependencies**: Java, Maven, WordNet. **You may need to set the right locale**, see [build.sh](build.sh). Unlike initial versions relying on LTI repositories, this is a self-sufficient one.

Most of the existing functionality can be initialized by calling the function [EphyraPart.initBasic](src/main/java/info/ephyra/EphyraPart.java#L23). Then, you can extract answer types and foci. An example of doing so is in [QuestionAnalysis.java](src/main/java/info/ephyra/questionanalysis/QuestionAnalysis.java#L260). This class can be invoked by calling the script [run_question_analysis.sh](run_question_analysis.sh).

