import { IUserRole } from "@/interfaces/data/user/iUserRole";

export interface IUser {
  username: string;
  firstname: string;
  lastname: string;
  role: IUserRole;
}
