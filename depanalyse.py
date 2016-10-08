#!/usr/bin/env python3

"""
Analyse maven-dependency-plugin report outputs.
You will need to complete a Maven build before you can use this utility.
"""

import argparse
import glob
import io
import os.path
import sys
import zipfile
from collections import defaultdict


def analyse_tree(filename, maven_home, out):
    """
    Read a file containing a Maven dependency tree (e.g. the output of the
    maven-dependency-report plugin), look at the JAR files it refers to
    (assuming they have already been downloaded into the Maven cache),
    and print the tree along with some extra information about each dependency.
    """
    total_jars = 0
    total_size_mb = 0
    total_classes = 0
    totals_by_scope = defaultdict(lambda: defaultdict(lambda: 0))

    with open(filename) as tree_file:
        for line in tree_file:
            dep_def = parse_line(line)
            if dep_def:
                jar = find_jar(dep_def, maven_home)
                info = jar_info(jar)
                print("{line}   size: {size_mb:.1f} Mb, classes: {class_count}"
                      .format(line=line.rstrip(), **info), file=out)
                total_jars += 1
                total_size_mb += info['size_mb']
                total_classes += info['class_count']
                scope = dep_def['scope'] if dep_def['scope'] else "no scope"
                totals_by_scope[scope]['jars'] += 1
                totals_by_scope[scope]['size_mb'] += info['size_mb']
                totals_by_scope[scope]['class_count'] += info['class_count']
            else:
                print(line, file=out)

    print("\n", file=out)

    for (scope, scope_totals) in totals_by_scope.items():
        print("{0}: JARs: {1}, size: {2:.1f} Mb, classes: {3}"
              .format(scope, scope_totals['jars'], scope_totals['size_mb'],
                      scope_totals['class_count']), file=out)

    print("\nTotal: JARs: {0}, size: {1:.1f} Mb, classes: {2}"
          .format(total_jars, total_size_mb, total_classes), file=out)

def parse_line(line):
    """Parse line of tree file to extract dependency information"""
    line = line.rstrip().lstrip(' -+|\\')
    parts = line.split(':')
    scope = None
    classifier = None
    if len(parts) == 4:
        [gid, aid, pkg, version] = parts
    elif len(parts) == 5:
        [gid, aid, pkg, version, scope] = parts
    elif len(parts) == 6:
        [gid, aid, pkg, classifier, version, scope] = parts
    else:
        return None
    if pkg == "pom":
        return None
    return {"group_id": gid, "artifact_id": aid, "packaging": pkg,
            "classifier": classifier, "version": version, "scope": scope}

def find_jar(dep_def, maven_home):
    """
    Find the location of a JAR within the Maven cache
    """
    path_parts = [maven_home, "repository"]
    path_parts.extend(dep_def["group_id"].split("."))
    path_parts.append(dep_def["artifact_id"])
    path_parts.append(dep_def["version"])
    jar_name_parts = [dep_def["artifact_id"], dep_def["version"]]
    if dep_def["classifier"]:
        jar_name_parts.append(dep_def["classifier"])
    path_parts.append("{0}.{1}".format("-".join(jar_name_parts), dep_def["packaging"]))
    path = os.path.join(*path_parts)
    return path

def jar_class_count(jar_file):
    """
    Find the class count in a JAR, including recursing into nested JARs
    """
    all_files = jar_file.namelist()
    result = len([f for f in all_files if f.endswith(".class")])
    sub_jars = [f for f in all_files if f.endswith(".jar")]
    for sub_jar_name in sub_jars:
        with zipfile.ZipFile(io.BytesIO(jar_file.read(sub_jar_name))) as sub_jar:
            result += jar_class_count(sub_jar)
    return result

def jar_info(filename):
    """
    Analyse the JAR and find the information we are interested in
    """
    stat = os.stat(filename)
    size_mb = stat.st_size / (1024.0 * 1024.0)

    with zipfile.ZipFile(filename) as jar:
        class_count = jar_class_count(jar)

    return {"size_mb": size_mb, "class_count": class_count}

def main():
    """Entry point"""
    parser = argparse.ArgumentParser(description="Analyse Maven dependency trees")
    parser.add_argument("--files", required=False, default="target/dependency-reports/*-tree.txt",
                        help="Maven dependency plugin output files to analyse")
    parser.add_argument("--mvnhome", required=False, default=os.path.expanduser("~/.m2"),
                        help="Maven home directory to search for JAR files")
    args = parser.parse_args()

    first = True
    for tree_filename in glob.glob(args.files):
        if not first:
            print("\n")
        first = False
        print("Analyzing {0}:\n".format(tree_filename))
        analyse_tree(tree_filename, args.mvnhome, sys.stdout)

if __name__ == '__main__':
    main()
