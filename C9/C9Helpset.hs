<?xml version='1.0' encoding='ISO-8859-1' ?>
<!DOCTYPE helpset PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 1.0//EN"
	"../dtd/helpset_1_0.dtd">
< helpset version="1.0">

<!-- Help frame title -->
< title> The New Calculator help</title>

<!-- maps : convert targets to URLs -->
<maps>
	<homeID>main</homeID>
	< mapref location="C9Helpmap.jhm"/>
</maps>

<!-- views: these are the tabbed windows in the Help application -->
<view>
	<name>TOC</name>
	<label>Table Of Contents</label>
	<type>javax.help.TOCView</type>
	<data>C9Helptoc.xml</data>
</view>

<view>
	<name>Index</name>
	<label>Index</label>
	<type>javax.help.IndexView</type>
	<data>C9Helpidx.xml</data>
</view>

<view>
	<name> Search</name>
	<label>Search</label>
	<type>javax.help.SearchView</type>
	<data engine="com.sun.java.help.search.DefaultSearchEngine">
	JavaHelpSearch
	</data>
</view>

<!-- properties and elements of the help frame -->
<presentation default="true" displayviewimages="false">
<name>main window</name>
<size width="700" height="400" />
<location x="200" y="200" />
< title> The New Calculator help</title>
<image>toplevelfolder</image>
< toolbar>
< helpaction> javax.help.BackAction</helpaction>
< helpaction> javax.help.ForwardAction</helpaction>
< helpaction> javax.help.SeparatorAction</helpaction>
< helpaction> javax.help.HomeAction</helpaction>
< helpaction> javax.help.ReloadAction</helpaction>
< helpaction> javax.help.SeparatorAction</helpaction>
< helpaction> javax.help.PrintAction</helpaction>
< helpaction> javax.help.PrintSetupAction</helpaction>
</toolbar>
</presentation>

<presentation>
<name>main</name>
<size width="400" height="400" />
<location x="200" y="200" />
< title> The New Calculator help</title>
</presentation>
</helpset>