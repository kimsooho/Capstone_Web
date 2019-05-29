<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
  $("button").click(function(){
    $("p").hide();
  });
});
</script>
</head>
<button>Click me</button>
<script type="text/javascript">

    function runPyScript(input){
        var jqXHR = $.ajax({
            type: "POST",
            url: "./test",
            async: false,
            data: { mydata: input }
        });

        return jqXHR.responseText;
    }

    $('button').click(function(){
        //datatosend = 'this is my matrix';
        result = runPyScript(datatosend);
        console.log('Got back ' + result);
    });

</script>
</body>
</html>

