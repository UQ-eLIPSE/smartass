# Gets the dependencies for smartass
COBRA="cobra-0.98.4"
COBRA_ZIP="cobra-0.98.4.zip"

mkdir ../lib

# Setup cobra
curl -O http://jaist.dl.sourceforge.net/project/xamj/Cobra%20HTML%20Toolkit/0.98.4/cobra-0.98.4.zip
unzip $COBRA_ZIP
cp $COBRA/lib/*.jar ../lib/

# Setup jDvi
curl -O https://www-sfb288.math.tu-berlin.de/jdvi/jDvi.jar
cp jDvi.jar ../lib/

# Clean up
rm -rv $COBRA $COBRA_ZIP jDvi.jar
