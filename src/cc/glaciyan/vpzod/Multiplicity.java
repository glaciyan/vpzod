package cc.glaciyan.vpzod;

public enum Multiplicity {
  Unspecified,
  None,
  NoneToOne,
  //NoneOrMany, // Many fulfils the same purpose
  One,
  OneToMany,
  Many
}
