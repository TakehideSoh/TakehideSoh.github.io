#!/usr/bin/perl

sub sieve {
    my (@xs) = @_;
    if (@xs == 0) {
	return ();
    }
    my $x = shift(@xs);
    return ($x, &sieve(grep($_ % $x != 0, @xs)));
}

print join(" ", &sieve(2 .. 100)), "\n";
exit 0;
