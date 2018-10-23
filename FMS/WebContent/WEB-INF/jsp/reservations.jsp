<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.syntelinc.fms.logic.queries.ReservationQuery"%>
<%@page import="com.syntelinc.fms.logic.Reservation"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="includes/head.jsp" %>
    <title>Reservations - Solr</title>
    <script type="text/javascript" src="resources/js/calendars.js"></script>
    <style>
        .fc-today {
            background-color: #ddd;
            color: #333;
        }

        .star {
            max-width: 20%;
            max-height: 30px;
        }
        
        .fc-event {
        	font-size: 1.25em;
        }
        
        .fc-event .fc-content {
        	text-align: center;
        	font-weight: bold;
        	color: #000000;
        }
        
        .fc-event .fc-content:hover {
        	color: #000000;
        }
        
        .background-icon {
        	width: 100%;
        	opacity: 0.3;
        	position: absolute;
        	padding-top: 5px;
        }
    </style>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="container-fluid">
    <div class="col-sm-12" id="cal">
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
                <form id="updateForm" method="post" action="updateReservation">
                	<input type="hidden" id="resID" name="resID" value="0" />
                    <fieldset>
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label class="control-label">Location</label>
                                    <p id="reservationModalLocation">2902 W Agua Fria Fwy<br />Phoenix, AZ 85027</p>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">Room</label>
                                    <!-- <input type="hidden" name="roomID" id="reservationModalRoomHidden" /> -->
                                    <!-- <p id="reservationModalRoom"></p> -->
                                    <select class="form-control" id="roomList" name="roomID">
                                    	<c:forEach var="room" items="${rooms}">
                                    		<option value="${room.getRoomID()}">${room.getRoomName()}</option>
   										</c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
				                    <label for="startTime" class="control-label">Start Time</label>
				                    <div>
				                        <div class="input-group date" id="startTimeDiv">
				                            <input type="text" id="startTime" name="reservationStart" class="form-control" />
				                            <span class="input-group-addon">
				                                <span class="far fa-calendar-alt"></span>
				                            </span>
				                        </div>
				                    </div>
				                </div>
                                <div class="form-group">
				                    <label for="endTime" class="control-label">End Time</label>
				                    <div>
				                        <div class="input-group date" id="endTimeDiv">
				                            <input type="text" id="endTime" name="reservationEnd" class="form-control" />
				                            <span class="input-group-addon">
				                                <span class="far fa-calendar-alt"></span>
				                            </span>
				                        </div>
				                    </div>
				                </div>
                                <div class="form-group">
                                    <label class="control-label">Duration</label>
                                    <p id="reservationModalDuration"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label for="purpose" class="control-label">Purpose</label>
                                    <textarea class="form-control" rows="5" id="purpose" name="reservationPurpose" style="resize:none;"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 text-center">
                                <div class="form-group">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                    <button type="button" class="btn btn-success" id="createReservationButton">Save Reservation</button>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="deleteConfirmationModal" id="deleteConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form id="deleteForm" method="post" action="deleteReservation">
                	<input type="hidden" id="deleteReservationID" name="resID" value="0" />
                    <fieldset>
                    	<div class="row">
                    		<h3 class="text-center">Are you sure you want to delete this reservation?</h3>
                    	</div>
                        <div class="row">
                            <div class="col-sm-12 text-center">
                                <div class="form-group">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                                    <button type="button" id="deleteConfirmButton" class="btn btn-danger">Yes</button>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
	var timesEditable = false;
	
    $(document).ready(function () {
        $("#calendar").fullCalendar({
            themeSystem: 'bootstrap3',
            defaultView: 'agendaWeek',
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
   					color: getInformation("${reservation.getReservationRoom().getRoomCategory()}").color,
   					icon: getInformation("${reservation.getReservationRoom().getRoomCategory()}").icon,
   					roomId: ${reservation.getReservationRoom().getRoomID()},
   					roomName: "${reservation.getReservationRoom().getRoomName()}",
   					locationStart: "${reservation.getReservationRoom().getRoomLocation().getLocationStreetAddress()}",
   					locationEnd: "${reservation.getReservationRoom().getRoomLocation().getLocationCity()}, ${reservation.getReservationRoom().getRoomLocation().getLocationState()}"
   				},
   				</c:forEach>
   			],
            eventRender: function (event, element) {
            	element.prepend('<span class="fa fa-' + event.icon + ' fa-fw text-center background-icon"></span>');
                element.popover({
                	trigger: "manual",
                	html: true,
                	container: 'body',
                	placement: (event.start.day() === 6) ? "left" : "right",
                	content: '<p>' + event.title + '</p>' +
                		'<p>Room: ' + event.roomName + '</p>' +
                		'<p>Start time: ' + event.start.format('MMMM DD, YYYY h:mm A') + '</p>' +
                		'<p>End time: ' + event.end.format('MMMM DD, YYYY h:mm A') + '</p>' +
                		'<button class="btn btn-success btn-sm editReservationButton" data-start-time="' + event.start + 
                		'" data-end-time="' + event.end + 
                		'" data-res-id="' + event.id + 
                		'" data-room-id="' + event.roomId + 
                		'" data-purpose="' + event.title + 
                		'" data-location-start="' + event.locationStart + 
                		'" data-location-end="' + event.locationEnd + 
                		'">Edit</button>&nbsp;<button type="button" class="btn btn-danger btn-sm deleteReservationButton" data-res-id="' + 
                		event.id + '">Delete</button>'
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
                });
            },
            eventAfterAllRender: function(){
                $(".background-icon").each(function(index, element){
                	var _this = $(this);
                	var parent = $(_this).parent();
                	
                	if ($(parent).height() < $(parent).width())
                		$(_this).css("font-size", $(parent).height() - 12);
                	else
                		$(_this).css("font-size", $(parent).width() - $(parent).width() / 4);
                });
            },
            nowIndicator: true,
            selectable: false,
            height: function () {
                return window.innerHeight - 100;
            }
        });
        
        $("#startTimeDiv, #endTimeDiv").datetimepicker({
            format: "MMMM D, YYYY h:mm A",
            useCurrent: false,
            stepping: 15
        });
        
        $("#startTimeDiv, #endTimeDiv").on("dp.change", function(e){
        	$("#reservationModalDuration").text(durationString($("#endTimeDiv").data("DateTimePicker").date() - $("#startTimeDiv").data("DateTimePicker").date()));
        })
        
        $("#startTimeDiv").on("dp.change", function(e){
        	if (!timesEditable)
        		return;
        	
        	var endTime = $("#endTimeDiv").data("DateTimePicker");
        	
        	if (e.date >= endTime.date())
       		{
        		endTime.date(endTime.date().add(e.date - e.oldDate));
			}

           	endTime.minDate(e.date.add(15, "minutes"));
        });
    });
    $(document).on("click", ".editReservationButton", function(){
    	var _this = $(this);
    	$("#reservationModalLocation").html(_this.data("location-start") + "<br />" + _this.data("location-end"));
    	$("#purpose").val(_this.data("purpose"));
    	$("#resID").val(_this.data("res-id"));
    	$("#roomList").val(_this.data("room-id"));
    	// Set end time first otherwise modal breaks
    	// Add 5 hours and add 15 minutes
       	$("#endTimeDiv").data("DateTimePicker").minDate(new Date(_this.data("start-time") + 18000000 + 900000));
    	// Add 5 hours
    	$("#endTimeDiv").data("DateTimePicker").date(new Date(_this.data("end-time") + 18000000));
    	// Add 5 hours
    	$("#startTimeDiv").data("DateTimePicker").date(new Date(_this.data("start-time") + 18000000));
    	
    	
    	$("#reservationModalDuration").text(durationString(_this.data("end-time") - _this.data("start-time")));
    	
    	$("#reservationModal").modal("show");
    }).on("click", ".deleteReservationButton", function(){
    	$("#deleteReservationID").val($(this).data("res-id"));
    	$("#deleteConfirmationModal").modal("show");
    });
    $("#reservationModal").on("hide.bs.modal", function(){
    	timesEditable = false;
    }).on("shown.bs.modal", function(){
    	timesEditable = true;
    }).on("show.bs.modal", function(){
    	$(".popover").popover("hide");
    });
    
    $("#deleteConfirmationModal").on("show.bs.modal", function(){
    	$(".popover").popover("hide");
    });
    
    $("#createReservationButton").on("click", function(){
       	$(this).attr("disabled", "disabled");
       	$(this).parent().append(loadingIcon("Saving..."));
       	$("#updateForm").submit();
    });
    
    $("#deleteConfirmButton").on("click", function(){
       	$(this).attr("disabled", "disabled");
       	$(this).parent().append(loadingIcon("Deleting..."));
       	$("#deleteForm").submit();
    });
</script>
</body>
</html>