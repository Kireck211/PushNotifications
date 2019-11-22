Parse.Cloud.define("hello", function(request, response){
 	response.success("Hello Class!");
});

Parse.Cloud.define("hello1", async function(request, response){
	const query = new Parse.Query("Testing");
	const results = await query.find();
 	response.success(results.map(function(result) {
		return result.testing;
	}));
});

Parse.Cloud.define("pushsample", function (request, response) {
    Parse.Push.send({
			channels: ["Course"],
            data: {
                title: "Hola Clase",
                alert: "Si funciona!",
            }
       }, {
            success: function () {
                // Push was successful
                response.success("push sent");
                console.log("Success: push sent");
            },
            error: function (error) {
                // Push was unsucessful
                response.error("error with push: " + error);
                console.log("Error: " + error);
            },
            useMasterKey: true
       });
});