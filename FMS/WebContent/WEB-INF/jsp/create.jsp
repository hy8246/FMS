<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="com.syntelinc.fms.logic.queries.RoomQuery"%>
<%@page import="com.syntelinc.fms.logic.Room"%>
<%@page import="com.syntelinc.fms.logic.queries.ReservationQuery"%>
<%@page import="com.syntelinc.fms.logic.Reservation"%>
<%@page import="java.util.List"%>
<%@page import="com.syntelinc.fms.logic.queries.FeatureQuery"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="includes/head.jsp" %>
    <title>New Reservation - Solr</title>
    <script type="text/javascript" src="resources/js/calendars.js"></script>
    <style>
        .fc-today {
            background-color: #ddd;
            color: #333;
        }

        #confetti-canvas {
            position: absolute;
            z-index: -1;
        }

        .star {
            max-width: 20%;
            max-height: 30px;
        }

        .fc-divider {
            background-color: #eee;
        }
        
        .fc-timeline-event {
        	height: 115px;
        }

        .fc-highlight {
            /*height: 100%;
            width: 100%;
            left:0;
            right: 0;
            top: 0;
            bottom: 0;
            position: absolute;*/
            opacity: 1;
            background: linear-gradient(124deg, #ff2400, #e81d1d, #e8b71d, #e3e81d, #1de840, #1ddde8, #2b1de8, #dd00f3, #dd00f3);
            background-size: 1800% 1800%;

            -webkit-animation: rainbow 18s ease infinite;
            -z-animation: rainbow 18s ease infinite;
            -o-animation: rainbow 18s ease infinite;
            animation: rainbow 18s ease infinite;}

        @-webkit-keyframes rainbow {
            0%{background-position:0% 82%}
            50%{background-position:100% 19%}
            100%{background-position:0% 82%}
        }
        @-moz-keyframes rainbow {
            0%{background-position:0% 82%}
            50%{background-position:100% 19%}
            100%{background-position:0% 82%}
        }
        @-o-keyframes rainbow {
            0%{background-position:0% 82%}
            50%{background-position:100% 19%}
            100%{background-position:0% 82%}
        }
        @keyframes rainbow {
            0%{background-position:0% 82%}
            50%{background-position:100% 19%}
            100%{background-position:0% 82%}
        }

        #realLink:hover {
            text-decoration: underline;
        }

        #fakeLink {
            text-decoration: none;
        }

        ul.pagination > li.active > a:hover {
            cursor: pointer;
        }

        ul.pagination > li > a:hover {
            background-color: #149c82;
        }
    </style>
</head>
<body>
<canvas id="confetti-canvas">
</canvas>
<%@ include file="includes/navbar.jsp" %>
<div class="container-fluid">
    <div class="col-md-2" id="filter">
        <div class="well">
            <h4>Filter Resources</h4>
            <form>
            	<div class="form-group">
            		<label class="control-label">Location</label>
            		<p><a href="location" title="Change Location" data-toggle="tooltip" data-placement="bottom">${loc.getLocationStreetAddress()}, ${loc.getLocationCity()}, ${loc.getLocationState()}</a></p>
            	</div>
            	<div class="form-group">
            		<label class="control-label">Date</label>
            		<p id="filterDate"></p>
            	</div>
            	<div class="form-group">
            		<label for="category" class="control-label">Room Category</label>
            		<select name="category" id="category" class="form-control">
            			<option value="Z">All</option>
            			<option value="C">Conference</option>
            			<option value="M">Meeting</option>
            			<option value="T">Training</option>
            			<option value="L">Lab</option>
            			<option value="R">Recreation</option>
            			<option value="X">Restricted</option>
            		</select>
            	</div>
            </form>
        </div>
    </div>
    <div class="col-md-10" id="cal">
        <div id="calendar"></div>
    </div>
</div>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="reservationModal" id="reservationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3>Reservation Information</h3>
            </div>
            <div class="modal-body">
                <form id="occurrenceForm" method="post" action="saveReservation">
                    <fieldset>
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label class="control-label">Location</label>
                                    <p id="reservationModalLocation">${loc.getLocationStreetAddress()}, <br />${loc.getLocationCity()}, ${loc.getLocationState()}</p>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Room</label>
                                    <input type="hidden" name="roomID" id="reservationModalRoomHidden" />
                                    <p id="reservationModalRoom"></p>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label class="control-label">Start Time</label>
                                    <input type="hidden" name="reservationStart" id="reservationModalStartTimeHidden" />
                                    <p id="reservationModalStartTime"></p>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">End Time</label>
                                    <input type="hidden" name="reservationEnd" id="reservationModalEndTimeHidden" />
                                    <p id="reservationModalEndTime"></p>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Duration</label>
                                    <p id="reservationModalDuration"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="panel-group" id="recurring" role="tablist" aria-multiselectable="true">
                                    <div class="panel panel-info">
                                        <div class="panel-heading" role="tab" id="headingOne">
                                            <h4 class="panel-title">
                                                <a id="fakeLink" class="collapsed" role="button" data-toggle="collapse" data-parent="#recurring" href="#recurringDetails" aria-expanded="false" aria-controls="recurringDetails">
                                                	<input type="hidden" id="isRecurring" name="recurring" value="false" />
                                                    <span class="far fa-square" id="fakeCheckbox"></span>&nbsp;&nbsp;<span id="realLink">Recurring Event</span>
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="recurringDetails" class="panel-collapse collapse" role="tabpanel" aria-labelledby="recurringDetails">
                                            <div class="panel-body">
                                                <div class="form-group row">
                                                    <div class="col-sm-3 col-xs-5">
                                                        <label class="control-label" for="recurring-days" style="margin-top:12px;">Repeat every</label>
                                                    </div>
                                                    <div class="col-sm-9 col-xs-7 input-group row">
                                                        <input type="number" class="form-control" name="everySpace" value="1" style="width:100px;" min="0" />
                                                        <select class="form-control" name="everyPeriod" style="width:104px;">
                                                            <option selected="selected" value="DAYS">day</option>
                                                            <option value="WEEKS">week</option>
                                                            <option value="MONTHS">month</option>
                                                            <option value="YEAR">year</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label">Repeat on</label><br />
                                                    <input type="hidden" id="monday" name="onMonday" value="false" />
                                                    <input type="hidden" id="tuesday" name="onTuesday" value="false" />
                                                    <input type="hidden" id="wednesday" name="onWednesday" value="false" />
                                                    <input type="hidden" id="thursday" name="onThursday" value="false" />
                                                    <input type="hidden" id="friday" name="onFriday" value="false" />
                                                    <ul class="pagination" style="margin-top:0;margin-bottom:0;">
                                                        <li><a href="#" class="recurring-day-of-week" data-day="#monday">M</a></li>
                                                        <li><a href="#" class="recurring-day-of-week" data-day="#tuesday">T</a></li>
                                                        <li><a href="#" class="recurring-day-of-week" data-day="#wednesday">W</a></li>
                                                        <li><a href="#" class="recurring-day-of-week" data-day="#thursday">T</a></li>
                                                        <li><a href="#" class="recurring-day-of-week" data-day="#friday">F</a></li>
                                                    </ul>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label">Ends</label>
                                                    <div class="radio" style="margin-top:0;">
                                                        <label class="col-sm-2 col-xs-3" style="margin-top:12px;">
                                                            <input type="radio" name="endType" id="onDate" value="endOnDate" checked="checked"> On
                                                        </label>
                                                        <div class="input-group date col-sm-6 col-xs-9" id="occurrenceDateCal">
                                                            <input type="text" id="occurrenceDate" name="endsOnDate" class="form-control" />
                                                            <span class="input-group-addon">
                                                                <span class="far fa-calendar-alt"></span>
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <div class="radio">
                                                        <label class="col-sm-2 col-xs-3" style="margin-top:12px;">
                                                            <input type="radio" name="endType" id="afterOccurrences" value="endsAfterTimes"> After
                                                        </label>
                                                        <div class="input-group col-sm-4 col-xs-9">
                                                            <input type="number" class="form-control" id="numberOfOccurrences" name="endsAfterTimes" value="1" readonly="readonly" min="0" />
                                                            <span class="input-group-addon">occurrences</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label for="purpose" class="control-label">Purpose</label>
                                    <input type="text" class="form-control" id="purpose" style="resize:none;" name="reservationPurpose"/>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="display:none;" id="superAdminInfo">
                        	<div class="col-sm-12">
	                        	<p><span class="fa fa-exclamation-circle" style="color:#EF0719;"></span>&nbsp;&nbsp;You are attempting to reserve a room that requires elevated permissions. To continue with the reservation, please enter credentials for a super-administrator:</p>
								<div class="form-group">
									<label class="control-label" for="superAdminUsername">Username</label>
									<input type="text" class="form-control" id="superAdminUsername" name="supername" />
								</div>
								<div class="form-group">
									<label class="control-label" for="superAdminPassword">Password</label>
									<input type="password" class="form-control" id="superAdminPassword" name="superpass" />
								</div>
							</div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 text-center">
                                <div class="form-group">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-success" id="createReservationButton">Create Reservation</button>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="instructions" tabindex="-1" role="dialog" aria-labelledby="instructions">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            </div>
            <div class="modal-body">
                <p class="visible-xs visible-sm">To reserve a room, touch and hold on the start time. Then, drag your finger to the end time and let go.</p>
                <p class="visible-md visible-lg">To reserve a room, click and drag on the period of time you would like to reserve.</p>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="recurringErrorModal" tabindex="-1" role="dialog" aria-labelledby="recurringErrorModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            	<h4 class="modal-title">Error in Processing Request</h4>
            </div>
            <div class="modal-body">
                <p class="text-center"><span class="fa fa-exclamation-circle fa-5x" style="color:#EF0719;"></span><br /><br />A recurring reservation you are trying to create conflicts with a pre-existing reservation. We are unable to process your request at this time.</p>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="restrictedErrorModal" tabindex="-1" role="dialog" aria-labelledby="restrictedErrorModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            	<h4 class="modal-title">Error in Processing Request</h4>
            </div>
            <div class="modal-body">
                <p class="text-center"><span class="fa fa-exclamation-circle fa-5x" style="color:#EF0719;"></span><br /><br />You attempted to reserve a room that is restricted and did not provide proper credentials to do so. Please contact your local location administrator for help in reserving this room.</p>
            </div>
        </div>
    </div>
</div>
<script>
    var confetti;
    
    function getUrlParam(name) {
        var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
        return (results && results[1]) || undefined;
    }
    
    function resourcesFunction() {
    	var resources = [
    			<c:forEach var="room" items="${rooms}">{
   				id: ${room.getRoomID()},
   				title: "${room.getRoomName()}",
   				category: "${room.getRoomCategory()}",
   				features: [
   	           		<c:forEach var="feature" items="${features.get(room.getRoomID())}">{
   	           			name: "${feature.getFeatureSetFeature().getFeatureDescription()}",
   	           			quantity: ${feature.getFeatureSetQuantity()}
           			},
   	           		</c:forEach>
   	           	]
   			},
   			</c:forEach>
       	];
    	var category = $("#category").val();
    	if (category === 'Z')
    		return resources;
    	else
    		return resources.filter(function( res ) {
    		    return res.category === category;
    		});
    }

    $(document).ready(function () {
    	if (getUrlParam("recurringError"))
    		$("#recurringErrorModal").modal("show");
    	else if (getUrlParam("restrictedError"))
    		$("#restrictedErrorModal").modal("show");
    	else
        	$("#instructions").modal("show");
    	
        $("#calendar").fullCalendar({
            themeSystem: 'bootstrap3',
            defaultView: 'timelineDay',
            header: {
                left: 'today',
                center: 'title',
                right: 'prev,next'
            },
            buttonText: {
                today: 'Today'
            },
            events: [
				<c:forEach var="reservation" items="${reservations}">{
					id: ${reservation.getReservationID()},
					title: "${reservation.getReservationPurpose()}",
					start: "${reservation.getReservationStart()}:00",
					end: "${reservation.getReservationEnd()}:00",
					resourceId: ${reservation.getReservationRoom().getRoomID()},
					color: getInformation("${reservation.getReservationRoom().getRoomCategory()}").color,
					description: ''
				},
				</c:forEach>
			],
			eventRender: function(event, element) {
				var start = moment(event.start);
				var end = moment(event.end);
				$(element).find(".fc-title").prepend(start.format("h:mm A") + " - " + end.format("h:mm A") + " ");
			},
            nowIndicator: true,
            select: function (startTime, endTime, jsEvent, view, resource) {
                $("#calendar").fullCalendar('addEventSource', [{
                    start: startTime,
                    end: endTime,
                    resourceId: resource.id,
                    id: "newEvent"
                }]);
                var info = getInformation(resource.category);
                $("#reservationModalRoom").html(iconify(info.icon, info.color, "2x") + resource.title);
                $("#reservationModalRoom").data("cal-event", resource.id);
                // $("#reservationModalRoomHidden").val("Room [roomID=" + resource.id + "]");
                $("#reservationModalRoomHidden").val(resource.id);
                $("#reservationModalStartTime").text(startTime.format('MMMM DD, YYYY h:mm A'));
                $("#reservationModalStartTime").data("cal-event", startTime);
                $("#reservationModalStartTimeHidden").val(startTime.format("YYYY-MM-DD[T]HH:mm:ss"));
                $("#reservationModalEndTime").text(endTime.format('MMMM DD, YYYY h:mm A'));
                $("#reservationModalEndTime").data("cal-event", endTime);
                $("#reservationModalEndTimeHidden").val(endTime.format("YYYY-MM-DD[T]HH:mm:ss"));
                
                if (resource.category === "X")
                	$("#superAdminInfo").show();
                else
                	$("#superAdminInfo").hide();

                $("#reservationModalDuration").text(durationString(endTime - startTime));
                $("#purpose").val("");

                $("#reservationModal").modal('show');
            },
            resourceLabelText: "Rooms",
            resources: function(callback) {
           		callback(resourcesFunction());
           	},
            resourceRender: function (resourceObj, $td) {
                var res = $td.eq(0).find('.fc-cell-content');
                var info = getInformation(resourceObj.category);
                res.addClass("text-center");
                res.prepend(iconify(info.icon, info.color, "5x") + "<br />");
                res.popover({
                	trigger: "hover",
                	html: true,
                	container: 'body',
                	title: resourceObj.title,
                	content: allFeatureIcons(resourceObj.features)
                });
            },
            resourceAreaWidth: 160,
            views: {
                timelineDay: {
                    selectable: true,
                    slotDuration: '00:15:00',
                    scrollTime: '08:00:00'
                }
            },
            viewRender: function(event) {
            	$("#filterDate").text(event.title);
            },
            selectOverlap: false,
            height: function () {
                return window.innerHeight - 100;
            }
        });
        $("#reservationModal").on("hide.bs.modal", function (e) {
            $("#calendar").fullCalendar("removeEvents", "newEvent");
        });
        $("#reservationModal").on("shown.bs.modal", function () {
            $("#purpose").focus();
        });
        $("#recurring").on('show.bs.collapse', function () {
            $("#fakeCheckbox").removeClass("fa-square").addClass("fa-check-square");
            $("#isRecurring").val("true");
        });
       $("#recurring").on('hide.bs.collapse', function () {
            $("#fakeCheckbox").addClass("fa-square").removeClass("fa-check-square");
            $("#isRecurring").val("false");
        });
        $(".recurring-day-of-week").on("click", function () {
            var _this = $(this);
            var parent = $(this).parent();
            _this.blur();
            parent.toggleClass("active");
            
            if (parent.hasClass("active"))
            	$(_this.data("day")).val("true");
            else
            	$(_this.data("day")).val("false");
        });
        $("#occurrenceDateCal").datetimepicker({
            format: "MMMM D, YYYY",
            defaultDate: new Date(),
            minDate: new Date()
        });
        $("#onDate").on("change", function () {
            $("#numberOfOccurrences").attr("readonly", "readonly");
            $("#occurrenceDateCal").data("DateTimePicker").enable();
        });
        $("#afterOccurrences").on("change", function () {
            $("#numberOfOccurrences").removeAttr("readonly");
            $("#occurrenceDateCal").data("DateTimePicker").disable();
        });
        $("#category").on("change", function() {
        	$("#calendar").fullCalendar("refetchResources");
        });
        confetti = new ConfettiGenerator({
            target: "confetti-canvas",
            colors: [
                [0, 241, 198],
                [0, 177, 146],
                [0, 101, 83],
                [0, 114, 93],
                [0, 75, 62],
                [224, 111, 29],
                [237, 117, 31],
                [198, 98, 26]
            ],
            max: 200
        });
        $('[data-toggle="tooltip"]').tooltip();
    });
    $("#createReservationButton").on("click", function(){
       	$(this).attr("disabled", "disabled");
       	$(this).parent().append(loadingIcon("Reserving..."));
    	$("#occurrenceForm").submit();
    });
</script>
</body>
</html>