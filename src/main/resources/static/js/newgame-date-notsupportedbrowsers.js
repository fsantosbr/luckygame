/*
It hideS the input datetime-local from a non supported browser and offer something else.
    https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/datetime-local
    I made some modification.
*/

 // define variables
 var nativePicker = document.querySelector('.nativeDateTimePicker');
 var fallbackPicker = document.querySelector('.fallbackDateTimePicker');
 var fallbackLabel = document.querySelector('.fallbackLabel');

 var yearSelect = document.querySelector('#year');
 var monthSelect = document.querySelector('#month');
 var daySelect = document.querySelector('#day');
 var hourSelect = document.querySelector('#hour');
 var minuteSelect = document.querySelector('#minute');

 // hide fallback initially
 fallbackPicker.style.display = 'none';
 fallbackLabel.style.display = 'none';

 // test whether a new datetime-local input falls back to a text input or not
 var test = document.createElement('input');

 try {
   test.type = 'datetime-local';
 } catch (e) {
   console.log(e.description);
 }

 // if it does, run the code inside the if() {} block
 if(test.type === 'text') {
   // hide the native picker and show the fallback
   nativePicker.style.display = 'none';
   fallbackPicker.style.display = 'block';
   fallbackLabel.style.display = 'block';

   // populate the days and years dynamically
   // (the months are always the same, therefore hardcoded)
   populateDays(monthSelect.value);
   populateYears();
   populateHours();
   populateMinutes();
 }

 function populateDays(month) {
   // delete the current set of <option> elements out of the
   // day <select>, ready for the next set to be injected
   while(daySelect.firstChild){
     daySelect.removeChild(daySelect.firstChild);
   }

   // Create variable to hold new number of days to inject
   var dayNum;

   // 31 or 30 days?
   if(month === '01' || month === '03' || month === '05' || month === '07' || month === '08' || month === '10' || month === '12') {
     dayNum = 31;
   } else if(month === '04' || month === '06' || month === '09' || month === '11') {
     dayNum = 30;
   } else {
   // If month is February, calculate whether it is a leap year or not
     var year = yearSelect.value;
     var isLeap = new Date(year, 1, 29).getMonth() == 1;
     isLeap ? dayNum = 29 : dayNum = 28;
   }

   // inject the right number of new <option> elements into the day <select>
   for(i = 1; i <= dayNum; i++) {
     var option = document.createElement('option');

      option.textContent = (i < 10) ? ("0" + i) : i;

     daySelect.appendChild(option);
   }


   // if previous day has already been set, set daySelect's value
   // to that day, to avoid the day jumping back to 1 when you
   // change the year
   if(previousDay) {
     daySelect.value = previousDay;

     // If the previous day was set to a high number, say 31, and then
     // you chose a month with less total days in it (e.g. February),
     // this part of the code ensures that the highest day available
     // is selected, rather than showing a blank daySelect
     if(daySelect.value === "") {
       daySelect.value = previousDay - 1;
     }

     if(daySelect.value === "") {
       daySelect.value = previousDay - 2;
     }

     if(daySelect.value === "") {
       daySelect.value = previousDay - 3;
     }
   }
 }

 function populateYears() {
   // get this year as a number
   var date = new Date();
   var year = date.getFullYear();


   // Making this year, ant the next 2 years available in the year <select>
   for(var x = 2; x >= 1; x--) {
     var option = document.createElement('option');
     option.textContent = year+x;
     yearSelect.appendChild(option);
   }

   // Creating the actual year in the year <select> and making it the default/selected year
    var option = document.createElement('option');
    option.textContent = year;
    yearSelect.appendChild(option);
    document.getElementById('year').value=year;
    // remove this line for the edit view

   // Make this year, and the 5 years before it available in the year <select>
   for(var i = 1; i <= 5; i++) {
     var option = document.createElement('option');
     option.textContent = year-i;
     yearSelect.appendChild(option);
   }


 }

 function populateHours() {
   // populate the hours <select> with the 24 hours of the day
   for(var i = 0; i <= 23; i++) {
     var option = document.createElement('option');
     option.textContent = (i < 10) ? ("0" + i) : i;
     hourSelect.appendChild(option);
   }
 }

 function populateMinutes() {
   // populate the minutes <select> with the 60 hours of each minute
   for(var i = 0; i <= 59; i++) {
     var option = document.createElement('option');
     option.textContent = (i < 10) ? ("0" + i) : i;
     minuteSelect.appendChild(option);
   }
 }

 // when the month or year <select> values are changed, rerun populateDays()
 // in case the change affected the number of available days
 yearSelect.onchange = function() {
   populateDays(monthSelect.value);
 }

 monthSelect.onchange = function() {
   populateDays(monthSelect.value);
 }

 //preserve day selection
 var previousDay;

 // update what day has been set to previously
 // see end of populateDays() for usage
 daySelect.onchange = function() {
   previousDay = daySelect.value;
 }

// This one is not from Mozila.org. They will update the hidden field with the values from the options
$(document).ready(function(){
  $("#day, #month, #year, #hour, #minute").change(function(){
      concatenated_string = $("#year").val() + "-" + $("#month").val() + "-" + $("#day").val() + "T" + $("#hour").val() + ":" + $("#minute").val();
      $("#closingDate").val(concatenated_string);
      $("#closingDate").text(concatenated_string)
  })
})