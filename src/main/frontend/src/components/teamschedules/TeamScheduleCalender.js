//달력 컴포넌트
import React, {useState, useEffect, useCallback} from "react";
import { format, subMonths, addMonths } from "date-fns";
import RenderHeader from "../calender/RenderHeader";
import RenderDays from "../calender/RenderDays";
import TeamRenderScheduleCells from "./TeamRenderScheduleCells";
import ScheduleCalender_CSS from "../../css/schedule_css/ScheduleCalender.css";

const TeamScheduleCalender = ({ onDateClick ,meetings, onUpdate,onRemove}) => {
  const [currentMonth, setCurrentMonth] = useState(new Date());
  const [selectedDate, setSelectedDate] = useState(new Date());
 
  const prevMonth = () => {
    setCurrentMonth(subMonths(currentMonth, 1));
  };
  const nextMonth = () => {
    setCurrentMonth(addMonths(currentMonth, 1));
  };

    const handleToggle= useCallback((day)=>{
    setSelectedDate(new Date(day));
    console.log("클릭한 날짜");
    console.log(new Date(day));
    onDateClick(new Date(day)); //부모 컴포넌트로 선택한 날짜 전달하기
  },[selectedDate]);
 
  return (
    <div className="Schedulecalendar">
      <RenderHeader
        currentMonth={currentMonth}
        prevMonth={prevMonth}
        nextMonth={nextMonth}
      />
      <RenderDays/>
      <TeamRenderScheduleCells
        currentMonth={currentMonth}
        selectedDate={selectedDate}
        onDateClick={handleToggle}
        meetings={meetings}
        onUpdate={onUpdate}
        onRemove ={onRemove}
      />
    </div>
  );
};
export default TeamScheduleCalender;