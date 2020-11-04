#!/opt/local/bin/gnuplot -persist
set terminal postscript eps enhanced color "Helvetica" 18
set terminal postscript eps color
set output "cactus.eps"
csv = "cactus.csv"
##
cactus(method) = sprintf("< echo 0; grep %s %s | grep -v 600 | cut -d',' -f 3 | sort -n", method, csv)
set key top left
set xlabel "#Solved" font "Helvetica,26"
set ylabel "Time (sec)" font "Helvetica,26"
set style data linespoints
set pointsize 0.9
set style line  1 lt 1 lc rgb "#00c000" lw 1 pt 1 ps 1.5
set style line  2 lt 1 lc rgb "#000000" lw 1 pt 2 ps 1.5
plot [0:119] [0:600] \
     cactus("OLD") title "IHCP (old-version)", \
     cactus("NEW") title "hss-v1-0-0 + post"
set term aqua
replot
quit