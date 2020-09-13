#!/bin/bash
program_name="famous-teacher-cms-1.0.0"
result=$(ps -ef | grep $program_name)
result_line=""

mvn package

while read -r line; do
	if [[ $line != *"grep"* ]]; then
		echo "$line"
		result_line="$line"
		break
	fi
done <<< "$result"

if [[ ! -z $result_line ]]; then
	result_array=($result_line)
	process_id="${result_array[1]}"
	echo "$program_name is running, process_id=$process_id, try to kill it..."
	$(kill $process_id)
	echo "previously running $program_name killed"
fi

echo "now starting $program_name"

nohup java -jar "target/$program_name.jar" >> "./$program_name.log" 2>&1 &
tail -f "./$program_name.log"