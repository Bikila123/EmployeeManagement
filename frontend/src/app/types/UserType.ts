import { BranchType } from "./BranchType";
import { PositionType } from "./PositionType";

export class UserType{
    id:number;
    username:String;
    first_name:String;
    last_name:String;
    middle_name: string;
    email: string;
    password:String;
    role_id:number;
    created_by:number;
    created_date:Date;
    is_loggedin:string;
    is_enabled:string;
    is_locked: string;
    isAccountNonExpired: number;
    otp:string;
    confirmpassword:string;
    phone_number: string;
    modified_by: number;
    modified_date: Date;
    jobPosition:PositionType;
    branch:BranchType;
    media:string;
    placeofwork:string;
    password_changed_date:Date;
    login_attempts:number;
    branch_id:number;
    position_id:number;
    empId:string;
}