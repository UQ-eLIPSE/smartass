<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<!-- 	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1"> -->
	
	<title>Interactive Editor</title>    

	<style type="text/css">
        @import "js/dojo1.2/dijit/themes/tundra/tundra.css";
        @import "js/dojo1.2/dojo/resources/dojo.css";
        html, body { height: 100%; width: 100%; margin: 0; padding: 0; }
	</style>    
    
	<!-- load the dojo toolkit base -->
 	<script type="text/javascript" src="js/dojo1.2/dojo/dojo.js"
	    djConfig="parseOnLoad:true, isDebug: false"></script> 

	<script type="text/javascript" >
		dojo.require("dojo.parser");
		dojo.require("dojo.data.ItemFileReadStore");
	    dojo.require("dijit.Toolbar");
	    dojo.require("dijit.layout.TabContainer");
	    dojo.require("dijit.layout.BorderContainer");
	    dojo.require("dijit.layout.ContentPane");
	    dojo.require("dijit.Tree");
	    dojo.require("dijit.form.SimpleTextarea");
	    dojo.require("dijit.form.Button");
	    dojo.require("dijit.form.Form");
    </script>

	<script type="text/javascript">
		function /*String*/ treeToText(/*Object*/ node) {
			var s = "";
			if(node.codehead!=undefined)
			 	s = s + node.codehead;
			if(node.children!=undefined)
				for(var i=0;i<node.children.length;i++) 
					s = s + treeToText(node.children[i]);;
			if(node.codetail!=undefined)
			 	s = s + node.codetail;
			return s;
		}

		function setCodeEditMode(edit) {
			dijit.byId("editCode").attr("disabled", edit);
			dijit.byId("saveCode").attr("disabled", !edit);
			dijit.byId("code").attr("readOnly", !edit);
		}

		function editCode() {
			setCodeEditMode(true);
			dijit.byId("code").focus();
			dijit.byId("codeTree").buildRendering();
		}

		function createTree() {
			console.debug("before tree created");
			try {
			codeTree = new dijit.Tree( {
				store: codeStore,
				labelAttr: "name",
				label: "Template",
				onClick: function(item) {
					setCodeEditMode(false);
					dojo.byId("codeNode").value = item.id;
					dijit.byId("code").attr("value", treeToText(item));
					}
				}, "codeTree");
			} catch (err) {}
			console.debug("aFTER tree created");
			}

		function loadCode() {
			dojo.xhrGet( { 
		        url: "interactive-template-tree.htm?name=<c:out value="${name}"/>", 
		        handleAs: "json",
		        preventCache: true,
				sync: true,
				
		        //timeout: 5000, 
		        load: function(responseObject, ioArgs) {
					//console.debug("response: " + responseObject.stamp);
		        	//console.dir(responseObject);
					codeStore.close();
					codeStore._jsonFileUrl = undefined; 
		        	codeStore._jsonData = responseObject;
					dijit.byId("codeTree").destroy(true);
					createTree();
					dijit.byId("editCode").attr("disabled", true);
					dijit.byId("code").attr("value", "");
		          	return response; 
				},
		        error: function(response, ioArgs) { 
			          console.error("HTTP status code: ", ioArgs.xhr.status); 
			          return response; 
			          }
			});
		}

		function loadResult(refresh, node) {
			codeStore.close();
			var the_url = "interactive-template-execute.htm?name=<c:out value="${name}"/>"; 
			if(refresh) 
				the_url = the_url + "&refresh=1";
			if(node)
				the_url = the_url + "&node=" + node;
			 
			dojo.xhrGet( { 
		        url: the_url, 
		        handleAs: "json",
		        preventCache: true,
				sync: true,
				
		        //timeout: 5000, 
		        load: function(responseObject, ioArgs) {
					//console.debug("response: " + responseObject.stamp);
		        	//console.dir(responseObject);
					resultStore.close();
					resultStore._jsonFileUrl = undefined; 
		        	resultStore._jsonData = responseObject;
		          	return responseObject; 
				},
		        error: function(response, ioArgs) { 
			          console.error("HTTP status code: ", ioArgs.xhr.status); 
			          return response; 
			          }
			});
		}

		function loadAndExecuteCode() {
			loadCode();
			dijit.byId("resultTree").destroy(true);
			loadResult(false);
			createResultTree();
		}
		
		function saveCode() {
			setCodeEditMode(false);
			console.debug("node id" + dojo.byId("codeNode").value);
			dojo.xhrPost( { 
		        url: "interactive-code-post-node.htm", 
		        handleAs: "text",
				sync: true,

		        //timeout: 5000, // Time in milliseconds

		        // The LOAD function will be called on a successful response.
		        load: function(response, ioArgs) {  
					//console.debug("put done");
					loadAndExecuteCode();
		          	return response; 
		        },

		        // The ERROR function will be called in an error case.
		        error: function(response, ioArgs) { 
		          console.error("HTTP status code: ", ioArgs.xhr.status); 
		          return response; 
		          },
				
		        form: "codeForm"  
		        });
		}


		function /*String*/ resultTreeToText(/*Object*/ node, /*int*/ index) {
			var s = "";
			if(node.code!=undefined && node.code[index]!=undefined)
			 	s = s + node.code[index];
			if(node.children!=undefined)
				for(var i=0;i<node.children.length;i++) 
					s = s + resultTreeToText(node.children[i], index);
			return s;
		}

		function setResultsTexts(node) {
			ch = dijit.byId("resultContainer").getChildren();
			for(var i=0;i<ch.length;i++) {
				dijit.byId("resultText"+ch[i].title).attr("value", 
						resultTreeToText(node, i));
				dijit.byId("resultPdf"+ch[i].title).setContent(
					'<applet code="PdfViewerApplet.class" height="100%" width="100%" archive="applet/PDFRenderer.jar, applet/PdfViewerApplet.jar" >' +
	        		'<param name="name" value="COMPOSER">' + 
	        		'<param name="node" value="' + selectedNodeId +'">' + 
	        		'<param name="section" value="' + ch[i].title +'">' + 
	        		'<param name="debug" value="true">' + 
	        		'</applet>');
			}
		}
		
		function createResultTree() {
			console.debug("before r-tree created");
			try {
				codeTree = new dijit.Tree( {
					store: resultStore,
					labelAttr: "name",
					label: "Template",
					onClick: function(item) {
						selectedNodeId = item.id;
						setResultsTexts(item);
						if(activePdfApplet!=undefined) 
							setPdfApplet(activePdfApplet);
/*						ch = dijit.byId("resultContainer").getChildren();
						for(var i=0;i<ch.length;i++)
							dijit.byId("resultText"+ch[i].title).attr("value", 
									resultTreeToText(item, i));*/
					}
				}, "resultTree");
			} catch (err) {}
			console.debug("aFTER r-tree created");
		}

		function setPdfApplet(container) {
/*    		container.setContent('<applet codebase="applet" code="au.edu.uq.smartass.web.iapp.PdfViewerApplet.class" height="100%" width="100%" >' +
	        		'<param name="name" value="<c:out value="${name}"/>">' + 
	        		'<param name="node" value="' + selectedNodeId +'">' + 
	        		'<param name="section" value="' + container.id.replace("resultPdf", "") +'">' + 
	        		'</applet>');*/
		}
		
		function initResultTabs() {
            console.debug("init tabs");
			dojo.xhrGet( {
		        url: "interactive-template-sections.htm?name=<c:out value="${name}"/>",
		        handleAs: "json",
		        sync: true,
		        
		        load: function(responseObject, ioArgs) {
		          console.debug(responseObject);

		          var container = dijit.byId("resultContainer");
//		          console.debug(container);
		        			            
		          for(var i=0; i<responseObject.sections.length;i++) {
//    		     	  console.debug("section["+i+"]: " + responseObject.sections[i]); 
					  //container for TeX and PDF content
					  var texPdfTabs = new dijit.layout.TabContainer({
							id: "texPdf"+responseObject.sections[i],
 				        	title : ""+responseObject.sections[i],
 				        	tabPosition: "bottom",
 				        	onClick: function(m) {
								if(this.selectedChildWidget.id.indexOf("Pdf")>-1) {
									activePdfApplet = this.selectedChildWidget;
			        				setPdfApplet(this.selectedChildWidget);
								} else
									activePdfApplet = undefined;
					  		} });
			          //container for TeX content
					  var tex = new dijit.layout.ContentPane({
			        	    id: "result"+responseObject.sections[i],
				        	title: "TeX"});
					  var pdf = new dijit.layout.ContentPane({
			        	    id: "resultPdf"+responseObject.sections[i],
				        	title: "PDF"
				        	/*href: "applet_inc.html",*/
		        		  });
//				      console.debug(tab);
					  var text = new dijit.form.SimpleTextarea( {
						  name : "result",
						  id : "resultText"+responseObject.sections[i],
						  rows : "30",
						  style : "height:99%;width:99%;",
						  readonly : "readonly"
					  	  });
			          console.debug("BEFORE SET CONTENT");
					  tex.setContent(text);
			          console.debug("after SET CONTENT");
			          try {
					  	container.addChild(texPdfTabs);
			          } catch (err) {}
			          try {
					  	texPdfTabs.addChild(tex);
			          } catch (err) {}
			          try {
					  	texPdfTabs.addChild(pdf);
			          } catch (err) {}
			          try {
					  	texPdfTabs.selectChild(tex);
			          } catch (err) {}
			          console.debug("after add child");
					  if(i==0)
				          try {
						  	container.selectChild(texPdfTabs);
				          } catch (err) {}
		          }
		          
	          	  container.startup();
		          return responseObject;
		        }
		});
		}

		function fetchResults() {
			dijit.byId("resultTree").destroy(true);
			createResultTree();
			console.debug("selectedNodeId:" + selectedNodeId);
			resultStore.fetchItemByIdentity({
				identity : selectedNodeId,
				onItem: function(item){
					console.debug("selectedNode:" + item);
					if(item!=undefined) {
						setResultsTexts(item);
					}
				}
			});
		}

		function executeAll() {
			loadResult(true);
			fetchResults();
		}

		function executeNode(node) {
			loadResult(false, node);
			fetchResults();
		}

		dojo.addOnLoad(function() {
			console.debug("create code tree");
			createTree();
			console.debug("create result tree");
			createResultTree();
			console.debug("init result tree");
			initResultTabs();
			activePdfApplet = undefined;
			console.debug("init before show");
			try {
				dijit.byId("rootDiv").attr('style', 'border: 0pt;margin: 0pt; height:100%;visibility:visible;');
			} catch(err) {}
			dijit.byId("progressMessage").setContent("");
			dijit.byId("progressMessage").attr('style', 'visibility:hidden;');
			dijit.byId("progressMessage").destroy(false);
			dijit.byId("appletLoader").setContent("");
			dijit.byId("rootDiv").resize();
		});

	</script>

    </head>
    <body class="tundra" style="text-align: center; min-width: 500px;">
		<div dojoType="dojo.data.ItemFileReadStore" jsId="codeStore" id="codeStore" clearOnClose="true"
              url="interactive-template-tree.htm?name=<c:out value="${name}"/>"></div>
              
		<div dojoType="dojo.data.ItemFileReadStore" jsId="resultStore" clearOnClose="true"
              url="interactive-template-execute.htm?name=<c:out value="${name}"/>"></div>
              
		<div id="progressMessage" style="border:0pt;margin:0pt;width: 50%; height: 100px; margin-left: auto;margin-right: auto;" dojoType="dijit.layout.ContentPane" >
			<table style="background-color: #22aaff;">
				<tr height="25%"></tr>
				<tr height="50%">
					<td width="25%"></td>
					<td width="50%"><b>Application is loading, please wait...</b></td>
					<td width="25%"></td>
				</tr>
				<tr height="25%"></tr>
			</table>
		</div>

		<div dojoType="dijit.layout.BorderContainer" design="headline" style="text-align: left; height:100%;visibility:hidden" id="rootDiv" >
 		   <div dojoType="dijit.layout.ContentPane" region="top">
 				<form method="post">
					<button id="reexecuteButton" dojoType="dijit.form.Button" onclick="executeAll">Execute All</button>
					<button style="width:125" dojoType="dijit.form.Button" type="submit" name="_eventId_save">Download results</button>
					<button style="width:125" dojoType="dijit.form.Button" type="submit" name="_eventId_edit">Return to editor</button>
			        <b>Interactive Editor
			        <div style="background: #ff8080"><CENTER>Attention! This application is experimental and can be unstable or work incorrectly!</CENTER></div>
			        </b>
				</form> 
		   </div>-->
 			<div dojoType="dijit.layout.TabContainer" region="center">
				<div dojoType="dijit.layout.BorderContainer" design="sidebar" style="height:100%;" title="Code" >
				   	<div splitter="true" id="codeTreeContainer" dojoType="dijit.layout.ContentPane" region="left"
						style="width:150pt;">
				                <div id="codeTree">
								</div>

				    </div>
 				   	<div dojoType="dijit.layout.ContentPane" region="top">
						<button id="editCode" dojoType="dijit.form.Button" onclick="editCode"  disabled="disabled">Edit</button>
						<button id="saveCode" dojoType="dijit.form.Button" onclick="saveCode" disabled="disabled">Save</button>
				   	</div> 
				    <div dojoType="dijit.layout.ContentPane" region="center">
						<form id="codeForm" style="height:100%;width:100%">
							<input type="hidden" name="name" value="<c:out value="${name}"/>"></input> 
							<input id="codeNode" type="hidden" name="node" value=""></input>
					        <textarea id="code" name="code" dojoType="dijit.form.SimpleTextarea" rows="30" style="height:99%;width:99%;" readonly="readonly"></textarea>
						</form>
				    </div>
				</div>

				<div dojoType="dijit.layout.BorderContainer" design="sidebar" style="height:100%;"  title="Result">
				   	<div splitter="true" id="resultTreeContainer" dojoType="dijit.layout.ContentPane" region="left"
						style="width:150pt;">
			               <div id="resultTree"></div>

				    </div>
 				   	<div dojoType="dijit.layout.ContentPane" region="top">
						<button id="editResult" dojoType="dijit.form.Button" onclick="editCode"  disabled="disabled">Edit</button>
						<button id="saveResult" dojoType="dijit.form.Button" onclick="saveCode" disabled="disabled">Save</button>
						<button id="executeNode" dojoType="dijit.form.Button" onclick="executeNode(selectedNodeId)" >Execute Node</button>
				   	</div> 
						<div dojoType="dijit.layout.TabContainer"  region="center" id="resultContainer">
						</div>
				</div>
			</div>  
		</div> 
		
		<div id="appletLoader" style="width:10px;height=10px" dojoType="dijit.layout.ContentPane" >
 			<applet code="PdfViewerApplet.class" height="100%" width="100%" archive="applet/PDFRenderer.jar, applet/PdfViewerApplet.jar" >
       		</applet> 		
		</div>
    </body>
</html>
<head>
<!-- 	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="-1"> -->
</head>

