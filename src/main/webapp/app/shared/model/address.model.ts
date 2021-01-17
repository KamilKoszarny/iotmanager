export interface IAddress {
  id?: number;
  city?: string;
  street?: string;
  streetNo?: string;
}

export class Address implements IAddress {
  constructor(public id?: number, public city?: string, public street?: string, public streetNo?: string) {}
}
