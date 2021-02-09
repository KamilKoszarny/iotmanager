import { AbstractAuditing, IAbstractAuditing } from '../../shared/model/abstract-auditing.model';

export interface IUser extends IAbstractAuditing {
  id?: any;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  password?: string;
}

export class User extends AbstractAuditing implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public password?: string
  ) {
    super();
  }
}
