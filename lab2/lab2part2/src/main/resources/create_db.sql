drop table if exists tags;
drop table if exists nodes;
/*<node id="27503928"
      version="36"
      timestamp="2015-01-29T11:42:06Z"
      uid="54441"
      user="SMP"
      changeset="28484183"
      lat="55.0282215"
      lon="82.9234476">*/

create table nodes(
          id bigint not null primary key,
          version bigint not null,
          timestamp date not null,
          uid bigint not null,
          username varchar (50) not null,
          changeset bigint not null,
          lat double precision not null,
          lon double precision not null,
          visible boolean
);

/*<tag k="name:ca"
     v="Novossibirsk"/>*/

create table tags(
         node_id bigint not null references nodes(id),
         key varchar(50) not null,
         value varchar(256) not null,
         primary key (node_id, key)
);