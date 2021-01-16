import { ISite } from 'app/shared/model/site.model';

export interface IAddress {
  id?: number;
  city?: string;
  street?: string;
  streetNo?: string;
  site?: ISite;
}

export class Address implements IAddress {
  constructor(public id?: number, public city?: string, public street?: string, public streetNo?: string, public site?: ISite) {}
}
