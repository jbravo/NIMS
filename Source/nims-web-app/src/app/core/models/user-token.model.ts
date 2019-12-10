export class UserPermission {
  grantedDomain: string;
  operationCode: string;
  resourceCode: string;
  defaultDomain: number;
}

export class UserMenu {
  name: string;
  code: string;
  url: string;
  reourceKey: string;
  sortOrder: string;
  sysMenuId: string;
}

export class UserToken {
  access_token: string;
  email: string;
  employeeCode: string;
  expires_in: number;
  fullName: string;
  loginName: string;
  phoneNumber: string;
  userId: number;
  loginTime: number;
  tokenExpiresIn: number;
  userPermissionList: UserPermission[];
  userMenuList: UserMenu[];
  sysGridView: any[];
  userInfo: any;
  deptIds: any[];
  locationIds: any[];
}
