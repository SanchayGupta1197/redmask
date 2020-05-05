(ColName text , pattern character default 'x', prefix integer default 0, Suffix integer default 0)
RETURNS text AS $$
DECLARE
    strlength integer;
BEGIN
    strlength:=length(ColName);
    IF prefix>0 then
        return substring(ColName FROM 1 FOR prefix )
          ||regexp_replace(substring(ColName FROM prefix+1 for (strlength-prefix-Suffix)),'[0-9]',pattern,'g')
          ||substring(ColName FROM (strlength-Suffix+1) FOR Suffix );
     END IF;
     return regexp_replace(substring(ColName FROM prefix+1 for (strlength-prefix-Suffix)),'[0-9]',pattern,'g')
          ||substring(ColName FROM (strlength-Suffix+1) FOR Suffix );
END;
$$
LANGUAGE plpgsql VOLATILE SECURITY INVOKER;


CREATE or replace function sql_replace(colname text,pattern character,prefix integer,suffix integer)
returns text AS $$
DECLARE strlength integer default 0;
BEGIN
     strlength = length($1);
     IF $3>0 then
          return substr($1,1,$3 )
          ||regexp_replace(substr($1,$3+1,(strlength-$3-$4)),'[0-9]',$2)
          ||substr($1,(strlength-$4+1),$4 );
     END IF;
     return regexp_replace(substr($1,$3+1,(strlength-$3-$4)),'[0-9]',$2)
     ||substr($1,(strlength-$4+1),$4 );
END;
$$ language SQL VOLATILE;