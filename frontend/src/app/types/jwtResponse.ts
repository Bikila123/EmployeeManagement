export interface JwtResponse {
  jwt: String;
  roleId: String;
  token: string;
	type: string;
	refreshToken:string;
	id: number;
	username: string;
	email: string;
	roles: string[];
	expired:boolean;
}
