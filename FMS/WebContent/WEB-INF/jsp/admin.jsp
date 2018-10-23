<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="com.syntelinc.fms.logic.queries.FeatureQuery"%>
<%@page import="com.syntelinc.fms.logic.queries.RoomQuery"%>
<%@page import="com.syntelinc.fms.logic.Room"%>
<%@page import="com.syntelinc.fms.logic.queries.ReservationQuery"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
    
<!DOCTYPE html>
<html>
<head>
    <%@ include file="includes/head.jsp" %>
    <title>Editor - Solr</title>
    <script type="text/javascript" src="resources/js/calendars.js"></script>
<style>
	#locationContent > div {
		background-image: url("resources/img/water.png");
		background-repeat: no-repeat; 
  		background-size: contain;
  		background-position: center;
  		height: 300px;		
	}
	
	.popoverdata {
		width: 300px;
		height: 300px;
		margin: auto;
	}

    #realLink:hover {
        text-decoration: underline;
    }

    #fakeLink {
        text-decoration: none;
    }
</style>
    
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="container-fluid">
	<div class="row" style="margin-top:25px;">
		<div class="col-lg-8 col-lg-offset-2">
			<div class="panel-group" id="recurring" role="tablist" aria-multiselectable="true">
                <div class="panel panel-info">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a id="fakeLink" role="button" data-toggle="collapse" data-parent="#recurring" href="#recurringDetails" aria-expanded="true" aria-controls="recurringDetails">
                                <span class="fa fa-angle-down" id="fakeCheckbox"></span>&nbsp;&nbsp;<span id="realLink">Room Options</span>
                            </a>
                        </h4>
                    </div>
                    <div id="recurringDetails" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="recurringDetails">
                        <div class="panel-body">
                            <form class="form-horizontal">
<!-- 	                            <div class="form-group"> -->
<!-- 									<label for="country" class="col-md-3 col-md-offset-2 control-label">Enter Country</label>  -->
<!-- 								 	<div class="col-md-4">    	 -->
<!-- 							        	<select class="form-control" id="country" name="country"> -->
<!-- 								        	<option value="UK">United Kingdom</option> -->
<!-- 								        	<option value="IND">India</option> -->
<!-- 								        	<option value="USA" selected="selected">United States</option> -->
<!-- 								        </select> -->
<!-- 									</div> -->
<!-- 								</div>	     -->
<!-- 							 	<div class="form-group"> -->
<!-- 							    	<label for="state" class="col-md-3  col-md-offset-2 control-label">Select State</label> -->
<!-- 							      	<div class="col-md-4"> -->
<!-- 							      		<select class="form-control" id="state"> -->
<!-- 											<option value="AL">Alabama</option> -->
<!-- 											<option value="AK">Alaska</option> -->
<!-- 											<option value="AZ" selected="selected">Arizona</option> -->
<!-- 											<option value="AR">Arkansas</option> -->
<!-- 											<option value="CA">California</option> -->
<!-- 											<option value="CO">Colorado</option> -->
<!-- 											<option value="CT">Connecticut</option> -->
<!-- 											<option value="DE">Delaware</option> -->
<!-- 											<option value="DC">District Of Columbia</option> -->
<!-- 											<option value="FL">Florida</option> -->
<!-- 											<option value="GA">Georgia</option> -->
<!-- 											<option value="HI">Hawaii</option> -->
<!-- 											<option value="ID">Idaho</option> -->
<!-- 											<option value="IL">Illinois</option> -->
<!-- 											<option value="IN">Indiana</option> -->
<!-- 											<option value="IA">Iowa</option> -->
<!-- 											<option value="KS">Kansas</option> -->
<!-- 											<option value="KY">Kentucky</option> -->
<!-- 											<option value="LA">Louisiana</option> -->
<!-- 											<option value="ME">Maine</option> -->
<!-- 											<option value="MD">Maryland</option> -->
<!-- 											<option value="MA">Massachusetts</option> -->
<!-- 											<option value="MI">Michigan</option> -->
<!-- 											<option value="MN">Minnesota</option> -->
<!-- 											<option value="MS">Mississippi</option> -->
<!-- 											<option value="MO">Missouri</option> -->
<!-- 											<option value="MT">Montana</option> -->
<!-- 											<option value="NE">Nebraska</option> -->
<!-- 											<option value="NV">Nevada</option> -->
<!-- 											<option value="NH">New Hampshire</option> -->
<!-- 											<option value="NJ">New Jersey</option> -->
<!-- 											<option value="NM">New Mexico</option> -->
<!-- 											<option value="NY">New York</option> -->
<!-- 											<option value="NC">North Carolina</option> -->
<!-- 											<option value="ND">North Dakota</option> -->
<!-- 											<option value="OH">Ohio</option> -->
<!-- 											<option value="OK">Oklahoma</option> -->
<!-- 											<option value="OR">Oregon</option> -->
<!-- 											<option value="PA">Pennsylvania</option> -->
<!-- 											<option value="RI">Rhode Island</option> -->
<!-- 											<option value="SC">South Carolina</option> -->
<!-- 											<option value="SD">South Dakota</option> -->
<!-- 											<option value="TN">Tennessee</option> -->
<!-- 											<option value="TX">Texas</option> -->
<!-- 											<option value="UT">Utah</option> -->
<!-- 											<option value="VT">Vermont</option> -->
<!-- 											<option value="VA">Virginia</option> -->
<!-- 											<option value="WA">Washington</option> -->
<!-- 											<option value="WV">West Virginia</option> -->
<!-- 											<option value="WI">Wisconsin</option> -->
<!-- 											<option value="WY">Wyoming</option> -->
<!-- 							    		</select> -->
<!-- 							    	</div> -->
<!-- 							   	</div> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label for="city" class="col-md-3 col-md-offset-2 control-label">Select City</label> -->
<!-- 								  	<div class="col-md-4"> -->
<!-- 							       		<select class="form-control" id="city" name="city"> -->
<!-- 				                        </select> -->
<!-- 								    </div> -->
<!-- 								 </div> -->
<!-- 							   	<div class="form-group"> -->
<!-- 								      <label for="address" class="col-md-3 col-md-offset-2 control-label">Enter Street Address</label> -->
<!-- 								      <div class="col-md-4"> -->
<!-- 							        	<select class="form-control" id="address" name="address"> -->
<!-- 				                        </select> -->
<!-- 								      </div> -->
<!-- 								 </div>    -->
							    <div class="form-group">
							     	<div class="col-lg-6 col-lg-offset-5">
<!-- 							        	<a href="home" class="btn btn-default" >Cancel</a> -->
<!-- 							        	<button type="button" class="btn btn-success" id="locationbutton">Set Location</button> -->
							      		<button type="button" class="btn btn-success" id="addroom">Add Room to Location</button>
							    	</div>
							 	</div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
		</div>
	</div><!-- Select Location -->
	
	<div class="row" id="locationContent">
		<c:if test="${not empty rooms}">
			<c:forEach var="oneroom" items="${rooms}">
				<div class="col-sm-4 text-center" style="margin-bottom: 20px;">
					<div class="popoverdata" data-room-id="${oneroom.roomID}" data-category="${oneroom.getRoomCategory()}" data-features='[<c:forEach var="feature" items="${features.get(oneroom.getRoomID())}">{"name":"${feature.getFeatureSetFeature().getFeatureDescription()}","quantity":${feature.getFeatureSetQuantity()}},</c:forEach>]'>
						<br>
						<h3 class="iconAfterThis">${oneroom.roomName}</h3>
						<br><br>
						<button class="btn btn-success editbutton" data-room-id="${ oneroom.roomID }" data-category="${oneroom.roomCategory}" data-description="${oneroom.roomDescription}" data-name="${oneroom.roomName}">Edit</button>
						<button class="btn btn-danger deletebutton" data-room-id="${ oneroom.roomID }">Delete</button>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>
</div>

<div class="modal fade" id="deleteModal" role="dialog">
	<div class="modal-dialog">
	  	<div class="modal-content">
    		<div class="modal-header" style="padding:35px 50px;">
			    <h4 class="modal-title text-center">Deleting this room will delete ALL reservations that have been made at this location.  Are you sure you want to DELETE this room?</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" action="deleteRoom" method="post" id="deleteForm">
					<input type="hidden" id="deleteRoomID" name="roomID" value="0" />
					<fieldset>
						<div class="form-group">
						      <div class="col-lg-9 col-lg-offset-3">
						        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
						        <button id="deleteRoomButton" type="button" class="btn btn-danger">Delete Room</button>
						      </div>
					    </div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div><!-- Delete Confirmation Modal  -->
<div class="modal fade" id="roomModal" role="dialog">
	<div class="modal-dialog">
  		<div class="modal-content">
			<div class="modal-body" style="padding:35px 50px;">
				<form class="form-horizontal" action="saveRoom" id="roomForm" method="POST">
					<input type="hidden" name="roomID" value="0" />
					<input type="hidden" name="locID" value="${loc.getLocationID()}" />
					<fieldset>
	 					<legend class="text-center">Add Room Details</legend>
	 					<div class="form-group">
		   					<label for="newRoomName" class="col-md-4 col-md-offset-1 control-label">Enter Room Name</label>
		   					<div class="col-md-6">
		    						<input type="text" id="newRoomName" name="roomName" class="form-control"/>
		   					</div>
	 					</div>	    
	 					<div class="form-group">
	   						<label for="newRoomDescription" class="col-md-4 col-md-offset-1 control-label">Enter Description</label>
	   						<div class="col-md-6">
	    							<textarea class="form-control" id="newRoomDescription" rows="3" name="roomDescription" style="resize:none;"></textarea>
	   						</div>
	 					</div>
						<div class="form-group">
				 			<label for="roomicon" class="col-md-4 col-md-offset-1 control-label">Select Room Type</label>
				      		<div class="col-md-6">
					        	<select name="roomCategory" class="form-control">
						        		<option value="T">Training</option>
						        		<option value="M">Meeting</option>
						        		<option value="C">Conference</option>
				   						<option value="L">Lab</option>
				   						<option value="R">Recreation</option>
			   							<option value="X">Restricted</option>
					        	</select>
				      		</div>
				 		</div> 
				 																
						<div class="form-group">
	    					<div class="col-lg-9 col-lg-offset-3">
	      					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	      					<button type="button" class="btn btn-success" id="newRoomButton">Save New Room</button>
	    					</div>
						</div>
				</fieldset>
			</form>
      	</div>
		</div>     
	</div>
</div><!-- Add Room Modal  -->
<div class="modal fade" id="editModal" role="dialog">
	<div class="modal-dialog">
  		<div class="modal-content">
			<div class="modal-body" style="padding:35px 50px;">
				<form class="form-horizontal" action="saveRoom" id="editForm" method="POST">
					<fieldset>
					   	<legend class="text-center">Edit Room Details</legend>
					   	<div class="form-group">
				     		<label for="editRoomName" class="col-sm-4 col-sm-offset-1 control-label">Edit Room Name</label>
			     			<div class="col-sm-6">
			      				<input type="text" name="roomName" id="editRoomName" class="form-control"/>
			     			</div>
					   	</div>	    
					   	<div class="form-group">
				     		<label for="editRoomDescription" class="col-sm-4 col-sm-offset-1 control-label">Edit Description</label>
				     		<div class="col-sm-6">
				      			<textarea class="form-control" rows="3" id="editRoomDescription" name="roomDescription" style="resize:none;"></textarea>
				     		</div>
					   	</div>
						<div class="form-group">
							<label for="editRoomCategory" class="col-sm-4 col-sm-offset-1 control-label">Edit Room Type</label>
			     			<div class="col-sm-6">
					        	<select name="roomCategory" id="editRoomCategory" class="form-control">
					        		<option value="T">Training</option>
					        		<option value="M">Meeting</option>
					        		<option value="C">Conference</option>
			   						<option value="L">Lab</option>
			   						<option value="R">Recreation</option>
			   						<option value="X">Restricted</option>
					        	</select>
			     			</div>
						</div>
						<input type="hidden" id="editRoomId" name="roomID"/>
						<input type="hidden" name="locID" value="${loc.getLocationID()}" />
						<div class="form-group">
					    	<div class="col-lg-9 col-lg-offset-3">
					        	<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					        	<button type="button" class="btn btn-success" id="saveroom">Save Room Edits</button>
					      	</div>
					   	</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div><!-- Edit Modal  -->
<div class="modal fade" id="featureModal" role="dialog">
	<div class="modal-dialog">
  		<div class="modal-content">
			<div class="modal-body" style="padding:35px 50px;">
				<form class="form-horizontal" action="saveFeatures" id="featureForm" method="POST">
					<fieldset>
						<input type="hidden" id="featureRoomId" name="roomID"/>
					   	<legend class="text-center">Edit Room Features</legend>
					   	<div class="form-group">
					   		<label for="featureChair" class="col-sm-4 col-sm-offset-1 control-label">Chairs</label>
			     			<div class="col-sm-6">
			      				<input type="number" name="chairs" id="featureChair" class="form-control" min="0" value="0" />
			     			</div>
					   	</div>
					   	<div class="form-group">
					   		<label for="featureDesktop" class="col-sm-4 col-sm-offset-1 control-label">Desktops</label>
			     			<div class="col-sm-6">
			      				<input type="number" name="desktops" id="featureDesktop" class="form-control" min="0" value="0" />
			     			</div>
					   	</div>
					   	<div class="form-group">
					   		<label for="featureWhiteboard" class="col-sm-4 col-sm-offset-1 control-label">Whiteboards</label>
			     			<div class="col-sm-6">
			      				<input type="number" name="whiteboards" id="featureWhiteboard" class="form-control" min="0" value="0" />
			     			</div>
					   	</div>
					   	<div class="form-group">
					   		<label for="featureProjector" class="col-sm-4 col-sm-offset-1 control-label">Projectors</label>
			     			<div class="col-sm-6">
			      				<input type="number" name="projectors" id="featureProjector" class="form-control" min="0" value="0" />
			     			</div>
					   	</div>
					   	<div class="form-group">
					   		<label for="featureTelevision" class="col-sm-4 col-sm-offset-1 control-label">Televisions</label>
			     			<div class="col-sm-6">
			      				<input type="number" name="televisions" id="featureTelevision" class="form-control" min="0" value="0" />
			     			</div>
					   	</div>
						<div class="form-group">
					    	<div class="col-lg-9 col-lg-offset-3">
					        	<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					        	<button type="button" class="btn btn-success" id="saveFeatures">Save Features</button>
					      	</div>
					   	</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div><!-- Feature Modal  -->

<script type="text/javascript">
	$(document).ready(function (){
// 		$("#country").val("${loc.getLocationCountry()}");
// 		$("#state").val("${loc.getLocationState()}");
// 		$("#city").append($("<option>", {
// 			value: "${loc.getLocationCity()}",
// 			selected: "selected"
// 		}).text("${loc.getLocationCity()}"));
// 		$("#address").append($("<option>", {
// 			value: "${loc.getLocationStreetAddress()}",
// 			selected: "selected"
// 		}).text("${loc.getLocationStreetAddress()}"));
		$("#addroom").click(function(){
			$("#newRoomName").val("");
			$("#newRoomDescription").val("");
			
            $("#roomModal").modal("show");
		});
        $("#locationbutton").click(function(){
        	$("#addroom").show();
        	$("#locationContent").show();
        	$("#recurringDetails").collapse("toggle");
        });
        $(".editbutton").click(function(){
        	$("#editRoomId").val($(this).data("room-id"));
        	$("#editRoomCategory").val($(this).data("category"));
        	$("#editRoomDescription").val($(this).data("description"));
        	$("#editRoomName").val($(this).data("name"));
        	
        	$("#editModal").modal("show");
        });
        
        $(".deletebutton").click(function(){
        	$("#deleteRoomID").val($(this).data("room-id"));
        	$("#deleteModal").modal("show");
        });
       
    	//$(".popoverdata"/*.children(".iconAfterThis")[0].append()*/;
    	$.each($(".popoverdata"), function(i, v) {
    		var _this = $(this);
    		var info = getInformation(_this.data("category"));
    		var features = parseMyJSON(_this.data("features"));
    		
    		var featureData = "";
    		$.each(features, function(i, v){
    			switch (v.name) {
    			case "CHAIR":
    				featureData += ' data-chair="' + v.quantity + '"';
    				break;
    			case "PROJECTOR":
    				featureData += ' data-projector="' + v.quantity + '"';
    				break;
    			case "WHITEBOARD":
    				featureData += ' data-whiteboard="' + v.quantity + '"';
    				break;
    			case "DESKTOP":
    				featureData += ' data-desktop="' + v.quantity + '"';
    				break;
    			case "TELEVISION":
    				featureData += ' data-television="' + v.quantity + '"';
    				break;
    			}
    		});
    		
    		_this.popover({
        		trigger: "manual",
        		html:true,
        		container:"body",
        		placement: (i % 3 === 2) ? "left" : "right",
        		title: "Room Details",
        		content: allFeatureIcons(features) + '<button type="button" class="btn btn-success btn-sm edit-features" data-room-id="' + _this.data("room-id") + '"' + featureData + '>Edit</button>'
       		}).on("mouseenter", function () {
                var _this = this;
                $(this).popover("show");
                $(".popover").on("mouseleave", function () {
                    $(_this).popover('hide');
                });
            }).on("mouseleave", function () {
                var _this = this;
                setTimeout(function () {
                    if (!$(".popover:hover").length) {
                        $(_this).popover("hide");
                    }
                }, 300);
            });;
    		_this.children(".iconAfterThis").after(iconify(info.icon, info.color, "6x"));
    	});
    	
	}).on("click", ".edit-features", function(){
    	//$("#deleteRoomID").val($(this).data("room-id"));
    	var _this = $(this);
    	$("#featureChair").val(_this.data("chair") || "0");
    	$("#featureProjector").val(_this.data("projector") || "0");
    	$("#featureDesktop").val(_this.data("desktop") || "0");
    	$("#featureWhiteboard").val(_this.data("whiteboard") || "0");
    	$("#featureTelevision").val(_this.data("television") || "0");
    	
    	$("#featureRoomId").val(_this.data("room-id"));
    	
    	$("#featureModal").modal("show");
    });
    $("#recurring").on('show.bs.collapse', function () {
        $("#fakeCheckbox").removeClass("fa-angle-right").addClass("fa-angle-down");
    });
    $("#recurring").on('hide.bs.collapse', function () {
        $("#fakeCheckbox").addClass("fa-angle-right").removeClass("fa-angle-down");
    });
    
    $("#featureModal").on("show.bs.modal", function(){
    	$(".popover").popover("hide");
    });
    
    $("#deleteRoomButton").on("click", function(){
       	$(this).attr("disabled", "disabled");
       	$(this).parent().append(loadingIcon("Deleting..."));
    	$("#deleteForm").submit();
    });
    
    $("#saveroom").on("click", function(){
       	$(this).attr("disabled", "disabled");
       	$(this).parent().append(loadingIcon("Saving..."));
    	$("#editForm").submit();
    });
    
    $("#newRoomButton").on("click", function(){
       	$(this).attr("disabled", "disabled");
       	$(this).parent().append(loadingIcon("Saving..."));
    	$("#roomForm").submit();
    });
    
    $("#saveFeatures").on("click", function(){
       	$(this).attr("disabled", "disabled");
       	$(this).parent().append(loadingIcon("Saving..."));
    	$("#featureForm").submit();
    });
    
</script>
</body>
</html>