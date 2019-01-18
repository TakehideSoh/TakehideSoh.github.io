VER = v1-0-2
VERSION = 1.0.2

APP0 = scarab
APP = $(APP0)-$(VER)
JAR = $(APP).jar
ZIP = $(APP).zip

WEBPAGE = http://kix.istc.kobe-u.ac.jp/~soh/soft/scarab/
WEBTITLE = Scarab: A Rapid Prototyping Tool for SAT-based Systems
JARS = lib/org.sat4j.core.jar
SRCS = src/jp/kobe_u/$(APP0)/*.scala src/jp/kobe_u/$(APP0)/*/*.scala

DOCTITLE = Scarab version $(VERSION) Core API Specification
SCALADOC  = scaladoc \
	-d docs/api \
	-doc-title '$(DOCTITLE)' \
	-doc-version '$(VERSION)' \
	-classpath classes:$(JARS) \
	-sourcepath src

all: scalac jar scaladoc zip

scalac:
	rm -rf classes/*
	mkdir -p classes
	cp -R org classes/
#	fsc -reset
#	fsc -sourcepath src -d classes -cp $(JARS) -optimise $(SRCS)
	scalac -deprecation -feature -sourcepath src -d classes -cp $(JARS) -optimise $(SRCS)

jar:
	jar cf lib/$(JAR) -C classes jp org
#	cp -p ../$(JAR) lib/
#	rm -rf ../$(JAR) 

scaladoc:
	rm -rf docs/api/*
	mkdir -p docs/api
	$(SCALADOC) $(SRCS)

zip:
	rm -f ../$(ZIP)
	rm -rf $(APP)
	mkdir $(APP)
	find ./src/ -name '.save.log' -exec rm '{}' \;	
	find ./docs/ -name '.save.log' -exec rm '{}' \;	
	cp -pr Makefile src lib docs examples $(APP)
	rm -f $(APP)/lib/$(APP0)*.jar $(APP)/lib/org.sat4j.core.jar $(APP)/examples/classes/*
	cp -pr lib/$(JAR) $(APP)/lib
	find $(APP) \( -name .svn -o -name CVS -o -name .cvsignore -o -name '*~' \) -exec rm -r '{}' '+'
	zip -q -r ../$(ZIP) $(APP)
	rm -rf $(APP)

web:
	cp ../$(ZIP) ~/web/www/scarab/
	rm -R ~/web/www/scarab/docs
	cp -r docs ~/web/www/scarab/

clean:
	rm -rf classes/*
	rm -rf docs/api/*

