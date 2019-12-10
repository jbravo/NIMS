import { Pagination } from './pagination.model';

export class CVListPartyMemberModel extends Pagination {
  employeeCode: string;
  employeeName: string;
  unit: number;
  employeeStatusAll: number;
  employeeStatusActive: number;
  employeeStatusDeactive: number;
  phoneNumber: string;
  email: string;
  identityNumber: string;
  passportNumber: string;
  title: string;
  objectType: string;
  officerNumber: string;
  taxCode: string;
  birthdayFrom: number;
  birthdayTo: number;
}
