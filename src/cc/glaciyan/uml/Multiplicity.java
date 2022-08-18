package cc.glaciyan.uml;

public enum Multiplicity {
  Unspecified,
  None,
  NoneToOne,
  //NoneOrMany, // Many fulfils the same purpose
  One,
  OneToMany,
  Many
}
