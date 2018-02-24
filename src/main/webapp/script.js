$(document).ready(function()
{
  function splitContent(content){
      var lines = content.split("\n");
      for(var i=0;i<lines.length;i++){
           addMethod(lines[i]);
      }
  }
  function loadProperties(){
      $.get('properties').success(function(content){
            splitContent(content)
      })
  }

  loadProperties();

  $("button").click(function(){
            $("#method").css("color","red")
            methodList = document.getElementById("method")
            if(methodList.selectedIndex != -1){
               var optionValue = methodList.options[methodList.selectedIndex].firstChild.nodeValue;
               var splitOption = optionValue.split(";")
                if(splitOption.length == 2){
                    var json = {method:splitOption[0] ,args:splitOption[1]};
                    $.post("/test",json,
                         		function(data,status){
                         			alert("数据: \n" + data + "\n状态: " + status);
                                }
                    )
                 }
                 }
  });
  function addMethod(method){
       var ele=$("<option></option>").text(method);
       $("#method").append(ele);
  }
  /* $.post("/test",{method:"keepon",args:"forever"},
      		function(data,status){
      			alert("数据: \n" + data + "\n状态: " + status);
  });

    var ele = lines[i].split("=");
              if(ele.length == 2){
                   if(ele[0] == "method"){
                        addMethod(ele[1]);
                   }
                   if(ele[0] == "args"){
                        addArgs(ele[1]);
                   }
              }*/
});
