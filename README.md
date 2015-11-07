EphyraQuestionAnalysis
======================

A collection of [OpenEphyra](http://sourceforge.net/projects/openephyra/) components necessary for question analysis. To compile one needs the Maven. **You may need to set the right locale**, see build.sh. Most of the existing functionality can be initialized by calling the function EphyraPart.initBasic. Then, you can extract answer types. An example of doing so is in src/main/java/info/ephyra/questionanalysis/QuestionAnalysis.java.

This was created primiarily for OAQA members. We pull some JARs from the OAQA repository. These JARs can also be found in the original OpenEphyra distribution. Many of them come (alas) without any source code.
